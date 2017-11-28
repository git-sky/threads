package cn.com.sky.threads.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer.ConditionObject;
import java.util.concurrent.locks.ReentrantLock;

public class TestAqsLock {

	public static void main(String[] args) {

		ReentrantLock lock = new ReentrantLock();

		lock.hasQueuedThreads();// 是否有线程在等待。
		lock.getHoldCount();// 当前线程锁重入的次数
		lock.getQueueLength();// 等待获取锁的线程队列长度
		lock.hasQueuedThread(new Thread());// thread是否在等待锁。
		lock.isFair();
		lock.isHeldByCurrentThread();// 是否当前线程持有了锁
		lock.isLocked();// 锁是否被占用

		// 独占锁
		lock.lock();// 锁不可被中断
		lock.unlock();



		try {
			lock.lockInterruptibly();// 锁可以被中断
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		lock.tryLock();// 试图加锁，失败后直接返回(非公平锁)
		try {
			lock.tryLock(100, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}




		// TODO
		ConditionObject c = (ConditionObject) lock.newCondition();
		try {
			c.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		c.signal();


        lock.hasWaiters(c);//是否有线程等待condition
        lock.getWaitQueueLength(c);//condition中等待线程的个数。

		CountDownLatch cdl = new CountDownLatch(0);
		try {
			cdl.await();
			// cdl.await(timeout, unit)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cdl.countDown();

	}

}
