package cn.com.sky.thread_juc.concurrent.locks.reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLockInterruptily {

	private Lock lock = new ReentrantLock();

	public static void main(String[] args) {

		TestLockInterruptily test = new TestLockInterruptily();

		MyThread t1 = new MyThread(test);
		MyThread t2 = new MyThread(test);
		t1.setName("t1");
		t2.setName("t2");
		t1.start();
		t2.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.interrupt();
	}

	public void insert(Thread thread) throws InterruptedException {
		try {
			//获取锁之后，不会再响应中断了；只有等待锁的时候，才能响应中断。
			lock.lockInterruptibly(); // 注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
			System.out.println(thread.getName() + " get lock ");
			long startTime = System.currentTimeMillis();
			for (;;) {
				if (System.currentTimeMillis() - startTime >= Integer.MAX_VALUE)
					break;
				// 插入数据
			}
		} finally {
			System.out.println(Thread.currentThread().getName() + "执行finally");
			lock.unlock();
			System.out.println(thread.getName() + " release lock ");
		}
	}
}

class MyThread extends Thread {

	private TestLockInterruptily test = null;

	public MyThread(TestLockInterruptily test) {
		this.test = test;
	}

	@Override
	public void run() {
		try {
			test.insert(Thread.currentThread());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(Thread.currentThread().getName() + "被中断");
		}
	}
}