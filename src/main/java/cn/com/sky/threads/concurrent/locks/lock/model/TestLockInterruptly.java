package cn.com.sky.threads.concurrent.locks.lock.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 
 * 当一个线程获取了锁之后，是不会被interrupt()方法中断的。
 * 单独调用interrupt()方法不能中断正在运行过程中的线程，只能中断阻塞过程中的线程。
 * 
 * lockInterruptibly(),当通过这个方法去获取锁时，如果线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态。
 * 
 * 因此当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，只有进行等待的情况下，是可以响应中断的。
 * 
 * 如果获取到锁了，则不能响应中断了。
 * 
 * 
 * 而用synchronized修饰的话，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。
 * 
 * 
 * 简而言之：
 * 等待锁时，lockInterruptibly可以响应中断，synchronized不能响应中断。
 * 获得锁之后，都不能响应中断 。
 * 
 */
public class TestLockInterruptly {

	public static void main(String[] args) {

	}

	public void method() throws InterruptedException {
		Lock lock = new ReentrantLock();
		lock.lockInterruptibly();
		try {
		} finally {
			lock.unlock();
		}
	}
}
