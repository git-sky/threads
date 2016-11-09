package cn.com.sky.threads.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * <pre>
 * 
 * 四、可重入锁：
 * 
 * 本文里面讲的是广义上的可重入锁，而不是单指JAVA下的ReentrantLock。
 * 
 * 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响。
 * 
 * 在JAVA环境下 ReentrantLock 和synchronized 都是 可重入锁。
 * 
 * 可重入锁最大的作用是避免死锁。
 *
 */
public class TestReentrant implements Runnable {

	public synchronized void get() {
		System.out.println(Thread.currentThread().getThreadGroup().getName()+":"+Thread.currentThread().getId()+":"+Thread.currentThread().getName());
		set();
	}

	public synchronized void set() {
		System.out.println(Thread.currentThread().getThreadGroup().getName()+":"+Thread.currentThread().getId()+":"+Thread.currentThread().getName());
	}

	@Override
	public void run() {
		get();
	}

	public static void main(String[] args) {
		TestReentrant ss = new TestReentrant();
		new Thread(ss).start();
		new Thread(ss).start();
		new Thread(ss).start();
	}
}

 class TestReentrantLock implements Runnable {
	 
	ReentrantLock lock = new ReentrantLock();

	public void get() {
		lock.lock();
		System.out.println(Thread.currentThread().getThreadGroup().getName()+":"+Thread.currentThread().getId()+":"+Thread.currentThread().getName());
		set();
		lock.unlock();
	}

	public void set() {
		lock.lock();
		System.out.println(Thread.currentThread().getThreadGroup().getName()+":"+Thread.currentThread().getId()+":"+Thread.currentThread().getName());
		lock.unlock();
	}

	@Override
	public void run() {
		get();
	}

	public static void main(String[] args) {
		TestReentrantLock ss = new TestReentrantLock();
		new Thread(ss).start();
		new Thread(ss).start();
		new Thread(ss).start();
	}
}
