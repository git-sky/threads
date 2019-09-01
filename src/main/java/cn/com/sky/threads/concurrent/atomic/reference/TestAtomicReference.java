package cn.com.sky.threads.concurrent.atomic.reference;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 * 
 * 原子更新引用类型
 * 
 * 原子更新基本类型的AtomicInteger，只能更新一个变量，如果要原子的更新多个变量，就需要使用这个原子更新引用类型提供的类。
 * 
 * Atomic包提供了以下三个类：
 * 
 * AtomicReference：原子更新引用类型。
 * 
 * AtomicReferenceFieldUpdater：原子更新引用类型里的字段。
 * 
 * AtomicMarkableReference：原子更新带有标记位的引用类型。可以原子的更新一个布尔类型的标记位和引用类型。
 * 构造方法是AtomicMarkableReference(V initialRef, boolean initialMark)
 * 
 * </pre>
 * 
 */
public class TestAtomicReference {

	public static AtomicReference<User> atomicUserRef = new AtomicReference<>();

	public static void main(String[] args) {

		User user = new User("A", 15);
		
		atomicUserRef.set(user);
		
		System.out.println(atomicUserRef.get().getName());
		System.out.println(atomicUserRef.get().getAge());

		System.out.println("====================================");
		
		User updateUser = new User("B", 17);

		atomicUserRef.compareAndSet(user, updateUser);

		System.out.println(atomicUserRef.get().getName());
		System.out.println(atomicUserRef.get().getAge());

	}

	static class User {
		
		private String name;
		private int age;

		public User(String name, int age) {
			this.name = name;
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public int getAge() {
			return age;
		}
	}

}