package cn.com.sky.threads.concurrent.test_unsafe;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

import java.util.Arrays;

/**
 * 
 * <pre>
 * 
 * arrayBaseOffset方法是一个本地方法，可以获取数组第一个元素的偏移地址。
 * 
 * arrayIndexScale方法也是一个本地方法，可以获取数组的转换因子，也就是数组中元素的增量地址。
 * 
 * 将arrayBaseOffset与arrayIndexScale配合使用， 可以定位数组中每个元素在内存中的位置。
 * 
 * </pre>
 * 
 */
@SuppressWarnings("restriction")
public class TestUnsafe3 {

	private static int byteArrayBaseOffset;

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
		theUnsafe.setAccessible(true);
		Unsafe UNSAFE = (Unsafe) theUnsafe.get(null);
		System.out.println(UNSAFE);

		byte[] data = new byte[10];
		System.out.println(Arrays.toString(data));
		byteArrayBaseOffset = UNSAFE.arrayBaseOffset(byte[].class);

		System.out.println(byteArrayBaseOffset);
		UNSAFE.putByte(data, byteArrayBaseOffset, (byte) 1);
		UNSAFE.putByte(data, byteArrayBaseOffset + 5, (byte) 5);
		System.out.println(Arrays.toString(data));
	}
}
