package cn.com.sky.threads.concurrent.test_unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * <pre>
 * 
 * Unsafe类提供了硬件级别的原子操作，主要提供了以下功能：
 * 1、通过Unsafe类可以分配内存，可以释放内存；
 * 类中提供的3个本地方法allocateMemory、reallocateMemory、freeMemory分别用于分配内存，扩充内存和释放内存，与C语言中的3个方法对应。
 * 2、可以定位对象某字段的内存位置，也可以修改对象的字段值，即使它是私有的；
 * 
 * 
 * compareAndSwapInt是通过反射根据字段偏移去修改对象。
 * 注意 Unsafe的对象不能直接new,要通过反射去获取。
 * 
 * 
 */
@SuppressWarnings("restriction")
public class TestUnsafe2 {

	private static Unsafe unsafe;

	static {
		try {
			// 通过反射获取rt.jar下的Unsafe类
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);// 通过字段获取对象:
												// 如果字段是静态字段的话,传入任何对象都是可以的,包括null。字段不是静态字段的话,要传入反射类的对象.如果传null是会报
												// java.lang.NullPointerException
		} catch (Exception e) {
			System.out.println("Get Unsafe instance occur error" + e);
		}
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws Exception {

		Class clazz = Target.class;
		Field[] fields = clazz.getDeclaredFields();

		System.out.println("fieldName:fieldOffset");

		for (Field f : fields) {
			// 获取属性偏移量，可以通过这个偏移量给属性设置
			System.out.println(f.getName() + ":" + unsafe.objectFieldOffset(f));// 返回指定静态field的内存地址偏移量,在这个类的其他方法中这个值只是被用作一个访问
																				// 特定field的一个方式。这个值对于
																				// 给定的field是唯一的，并且后续对该方法的调用都应该
																				// 返回相同的值。
		}

		Target target = new Target();
		Field intFiled = clazz.getDeclaredField("intParam");
		int a = (Integer) intFiled.get(target);
		System.out.println("intParam原始值是:" + a);
		// intParam的字段偏移是12 原始值是3 我们要改为10

		// 在obj的offset位置比较integer field和期望的值，如果相同则更新。
		// unsafe.compareAndSwapInt(o, offset, expected, x);

		System.out.println(unsafe.compareAndSwapInt(target, 12, 3, 10));
		int b = (Integer) intFiled.get(target);
		System.out.println("改变之后的值是:" + b);

		// 这个时候已经改为10了,所以会返回false
		System.out.println(unsafe.compareAndSwapInt(target, 12, 3, 10));

        /**
         * compareAndSwapObject(Object var1, long var2, Object var3, Object var4)
         * var1 操作的对象
         * var2 操作的对象属性
         * var3 var2与var3比较，相等才更新
         * var4 更新值
         */
		System.out.println(unsafe.compareAndSwapObject(target, 24, null, "5"));

		Field strParamField = clazz.getDeclaredField("strParam");
		String s = (String) strParamField.get(target);
		System.out.println(s);
	}
}

class Target {
	int intParam = 3;
	long longParam;
	String strParam;
	String strParam2;
}
