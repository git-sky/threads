package cn.com.sky.threads.concurrent.locks.readwritelock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 * 
 * 如果有一个线程已经占用了读锁，则此时其他线程如果要申请写锁，则申请写锁的线程会一直等待释放读锁。
 * 如果有一个线程已经占用了写锁，则此时其他线程如果申请写锁或者读锁，则申请的线程会一直等待释放写锁。
 * 
 */
public class TestReadWriteLock {

	private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	public static void main(String[] args) {

		final TestReadWriteLock test = new TestReadWriteLock();

		new Thread() {
			public void run() {
				test.get(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				test.get(Thread.currentThread());
			};
		}.start();

	}

	public void get(Thread thread) {
		rwl.readLock().lock();
		try {
			System.out.println(thread.getName() + "正在进行读操作");
			Thread.sleep(3000);
			System.out.println(thread.getName() + "读操作完毕");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rwl.readLock().unlock();
		}
	}
}