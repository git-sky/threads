package cn.com.sky.thread_juc.concurrent.atomic.array;

import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * <pre>
 * 
 * 原子更新数组类
 * 
 * 通过原子的方式更新数组里的某个元素，Atomic包提供了以下三个类：
 * AtomicIntegerArray：原子更新整型数组里的元素。
 * AtomicLongArray：原子更新长整型数组里的元素。
 * AtomicReferenceArray：原子更新引用类型数组里的元素。
 * 
 * AtomicIntegerArray类主要是提供原子的方式更新数组里的整型，其常用方法如下：
 * int addAndGet(int i, int delta)：以原子方式将输入值与数组中索引i的元素相加。
 * boolean compareAndSet(int i, int expect, int update)：如果当前值等于预期值，则以原子方式将数组位置i的元素设置成update值。
 * 
 * </pre>
 * 
 */
public class TestAtomicIntegerArray {

	static int[] value = new int[] { 1, 2 };

	static AtomicIntegerArray array = new AtomicIntegerArray(value);

	public static void main(String[] args) {

		array.getAndSet(0, 3);

		System.out.println(array.get(0));//不更改原来的数组，clone的新数组。

		System.out.println(value[0]);

	}

}