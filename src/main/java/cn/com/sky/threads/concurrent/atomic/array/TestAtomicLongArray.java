package cn.com.sky.threads.concurrent.atomic.array;

import java.util.concurrent.atomic.AtomicLongArray;

public class TestAtomicLongArray {
	
	
	static long[] value = new long[] { 1, 2 };

	static AtomicLongArray ai = new AtomicLongArray(value);

	public static void main(String[] args) {

		ai.getAndSet(0, 3);

		System.out.println(ai.get(0));

		System.out.println(value[0]);

	}

}
