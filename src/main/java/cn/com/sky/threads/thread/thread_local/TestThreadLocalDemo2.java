package cn.com.sky.threads.thread.thread_local;

import java.util.Random;

/**
 */
public class TestThreadLocalDemo2 implements Runnable {
	// 创建线程局部变量studentLocal，在后面你会发现用来保存Student对象
	private final static ThreadLocal<Student2> studentLocal = new ThreadLocal<Student2>() {
		@Override
		protected Student2 initialValue() {

			Student2 student = new Student2();
			School2 s = new School2();
			s.setName("beijing");
			student.setSchool(s);
			student.setAge(12);

			return student;
		}
	};

	public static void main(String[] agrs) {
		TestThreadLocalDemo2 td = new TestThreadLocalDemo2();
		Thread t1 = new Thread(td, "a");
		Thread t2 = new Thread(td, "b");
		t1.start();
		t2.start();
	}

	public void run() {
		accessStudent();
	}

	public void accessStudent() {
		// 获取当前线程的名字
		String currentThreadName = Thread.currentThread().getName();
		System.out.println(currentThreadName + " is running!");
		// 产生一个随机数并打印
		Random random = new Random();
		int age = random.nextInt(100);
		System.out.println("thread " + currentThreadName + " set age to:" + age);
		// 获取一个Student对象，并将随机数年龄插入到对象属性中
		Student2 student = studentLocal.get();
		System.out.println("thread " + currentThreadName + " first read age is:" + student.getAge() + " school:" + student.getSchool().getName());
		student.getSchool().setName("qinghua:" + age);
		student.setAge(age);
		System.out.println("thread " + currentThreadName + " first read age is:" + student.getAge() + " school:" + student.getSchool().getName());
		try {
			Thread.sleep(500);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		System.out.println("thread " + currentThreadName + " second read age is:" + student.getAge() + " school:" + student.getSchool().getName());
	}

}

class Student2 {
	private int age = 0; // 年龄

	private School2 school;

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public School2 getSchool() {
		return school;
	}

	public void setSchool(School2 school) {
		this.school = school;
	}
}

class School2 {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
