package cn.com.sky.threads.concurrent.atomic.reference;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 
 * <pre>
 * 
 * 
 * AtomicReference：原子更新引用类型。
 * AtomicReferenceFieldUpdater：原子更新引用类型里的字段。
 * AtomicMarkableReference：原子更新带有标记位的引用类型。可以原子的更新一个布尔类型的标记位和引用类型。构造方法是AtomicMarkableReference(V initialRef, boolean initialMark)
 * 
 * 
 * AtomicReferenceFieldUpdater 一个基于反射的工具类，它能对指定类的指定的volatile引用字段进行原子更新。(注意这个字段不能是private的)
 * 
 * 通过调用AtomicReferenceFieldUpdater的静态方法newUpdater就能创建它的实例，该方法要接收三个参数：
 * 
 * 1.被更新字段所属的类
 * 
 * 2.被更新字段的类型
 * 
 * 3.被更新字段的名称
 * 
 */
public class TestAtomicReferenceFieldUpdater2 {

	public static void main(String[] args) throws Exception {
		AtomicReferenceFieldUpdater<Person, Sex> updater = AtomicReferenceFieldUpdater.newUpdater(Person.class, Sex.class, "sex");

		Person person = new Person();

		System.out.println(person.name);
		System.out.println(person.sex);

		System.out.println("============================");

		Sex sex = new Sex("man");

		updater.compareAndSet(person, person.sex, sex);

		System.out.println(person.name);
		System.out.println(person.sex);

	}

}

class Person {
	volatile String name = "Jack";
	volatile Sex sex;
}

class Sex {

	private String sex;

	public Sex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "Sex [sex=" + sex + "]";
	}

}
