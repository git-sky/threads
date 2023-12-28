package cn.com.sky.thread_juc.concurrent.locks.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestTryLock {

	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {

		final TestTryLock test = new TestTryLock();

		Thread t1 = new Thread() {
			public void run() {
				test.insert(Thread.currentThread());
			};
		};

		t1.setName("t1");
		t1.start();

		Thread t2 = new Thread() {
			public void run() {
				test.insert(Thread.currentThread());
			};
		};

		t2.setName("t2");
		t2.start();
		
		
	}

	public void insert(Thread thread) {
		if (lock.tryLock()) {// 如果立即获得锁，返回true，否则返回false。
			try {
				System.out.println(thread.getName() + " get lock");
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println(thread.getName() + " release lock ");
				lock.unlock();
			}
		} else {
			System.out.println(thread.getName() + " get lock failure ");
		}
	}
}