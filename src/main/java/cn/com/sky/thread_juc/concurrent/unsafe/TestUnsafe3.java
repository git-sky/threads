package cn.com.sky.thread_juc.concurrent.unsafe;

import sun.misc.Unsafe;

import java.util.Arrays;

/**
 * <pre>
 *
 * arrayBaseOffset方法是一个本地方法，可以获取数组第一个元素的偏移地址。
 *
 * arrayIndexScale方法也是一个本地方法，可以获取数组的转换因子，也就是数组中元素的增量地址。
 *
 * 将arrayBaseOffset与arrayIndexScale配合使用， 可以定位数组中每个元素在内存中的位置。
 *
 * </pre>
 */
@SuppressWarnings("restriction")
public class TestUnsafe3 {

    private static int byteArrayBaseOffset;

    private static int arrayIndexScale;


    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        Unsafe unsafe = UnsafeUtil.getUnsafe();
        System.out.println(unsafe);

        byte[] data = new byte[10];
        System.out.println(Arrays.toString(data));
        byteArrayBaseOffset = unsafe.arrayBaseOffset(byte[].class);

        arrayIndexScale = unsafe.arrayIndexScale(byte[].class);

        System.out.println(byteArrayBaseOffset);
        unsafe.putByte(data, byteArrayBaseOffset, (byte) 1);
        unsafe.putByte(data, byteArrayBaseOffset + 5, (byte) 5);
        System.out.println(Arrays.toString(data));

        System.out.println(arrayIndexScale);
    }

}
