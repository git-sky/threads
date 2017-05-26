package cn.com.sky.threads.concurrent.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 在Condition中，
 * 用await()替换wait()，
 * 用signal()替换notify()，
 * 用signalAll()替换notifyAll()，
 * 
 * 传统线程的通信方式， Condition都可以实现，这里注意，Condition是被绑定到Lock上的，要创建一个Lock的Condition必须用newCondition()方法。
 * 
 * </pre>
 *
 */
public class TestCondition {

	public static void main(String[] args) {
		final BusinessDemo business = new BusinessDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				threadExecute(business, "sub");
			}
		}).start();
		threadExecute(business, "main");
	}

	public static void threadExecute(BusinessDemo business, String threadType) {
		for (int i = 0; i < 100; i++) {
			try {
				if ("main".equals(threadType)) {
					business.master(i);
				} else {
					business.slave(i);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class BusinessDemo {
	private boolean bool = true;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public void master(int loop) throws InterruptedException {
		lock.lock();
		try {
			while (bool) {
				condition.await();// this.wait();
			}
			for (int i = 0; i < 100; i++) {
				System.out.println("main thread seq of " + i + ", loop of " + loop);
			}
			bool = true;
			condition.signal();// this.notify();
		} finally {
			lock.unlock();
		}
	}

	public void slave(int loop) throws InterruptedException {
		lock.lock();
		try {
			while (!bool) {
				condition.await();// this.wait();
			}
			for (int i = 0; i < 10; i++) {
				System.out.println("sub thread seq of " + i + ", loop of " + loop);
			}
			bool = false;
			condition.signal();// this.notify();
		} finally {
			lock.unlock();
		}
	}
}