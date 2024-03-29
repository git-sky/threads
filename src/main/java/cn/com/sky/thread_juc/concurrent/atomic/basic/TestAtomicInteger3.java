package cn.com.sky.thread_juc.concurrent.atomic.basic;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger3 {

	AtomicInteger counter = new AtomicInteger(0);

	public int count() {
		return counter.incrementAndGet();
	}

	public static void main(String[] args) {
		final TestAtomicInteger3 c = new TestAtomicInteger3();

		new Thread(() -> c.count()).start();

		new Thread(() -> c.count()).start();

		new Thread(() -> c.count()).start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(c.counter.get());
	}
}
