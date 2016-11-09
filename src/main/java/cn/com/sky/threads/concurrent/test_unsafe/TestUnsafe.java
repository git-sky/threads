package cn.com.sky.threads.concurrent.test_unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * 使用Unsafe创建实例而不是调用构造方法
 */
@SuppressWarnings("restriction")
public class TestUnsafe implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Unsafe UNSAFE;

	static {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			UNSAFE = (Unsafe) theUnsafe.get(null);
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	public static void main(String[] args) {
		// new MyTest(10);

		Class<MyTest> aClass = MyTest.class;
		try {
			MyTest a = (MyTest) UNSAFE.allocateInstance(aClass);
			System.out.println(a.getNum());
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
}

class MyTest {

	private final int num;

	public MyTest(int num) {
		System.out.println("Hello Mum");
		this.num = num;
	}

	public int getNum() {
		return num;
	}
}