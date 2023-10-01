package cn.com.sky.threads.concurrent.atomic.field;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 
 * <pre>
 * 
 * 原子更新字段类
 * 
 * 如果我们只需要某个类里的某个字段，那么就需要使用原子更新字段类，Atomic包提供了以下三个类：
 * 
 * AtomicIntegerFieldUpdater：原子更新整型的字段的更新器。
 * 
 * AtomicLongFieldUpdater：原子更新长整型字段的更新器。
 * 
 * AtomicStampedReference：原子更新带有版本号的引用类型。该类将整数值与引用关联起来，可用于原子的更数据和数据的版本号，可以解决使用CAS进行原子更新时，可能出现的ABA问题。
 * 
 * 原子更新字段类都是抽象类，每次使用都时候必须使用静态方法newUpdater创建一个更新器。
 *
 * 原子更新类的字段的必须使用public volatile修饰符。
 * 
 * AtomicIntegerFieldUpdater的例子代码如下：
 * 
 * </pre>
 * 
 */
public class TestAtomicIntegerFieldUpdater2 {

	static String fieldName = "age";
	private static AtomicIntegerFieldUpdater<User> updater = AtomicIntegerFieldUpdater.newUpdater(User.class, fieldName);

	public static void main(String[] args) {

		User user = new User("zhangsan", 10);

		System.out.println(updater.getAndIncrement(user));
		System.out.println(updater.getAndIncrement(user));
		System.out.println(updater.getAndIncrement(user));

		System.out.println(updater.addAndGet(user, 10));

		
		System.out.println(updater.get(user));

	}

	public static class User {

		private String name;
		public volatile int age;

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
