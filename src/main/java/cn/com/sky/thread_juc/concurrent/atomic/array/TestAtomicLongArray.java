package cn.com.sky.thread_juc.concurrent.atomic.array;

import java.util.concurrent.atomic.AtomicLongArray;

public class TestAtomicLongArray {
	
	
	static long[] value = new long[] { 1, 2 };

	static AtomicLongArray array = new AtomicLongArray(value);

	public static void main(String[] args) {

		array.getAndSet(0, 3);

		System.out.println(array.get(0));

		System.out.println(value[0]);

	}

}
