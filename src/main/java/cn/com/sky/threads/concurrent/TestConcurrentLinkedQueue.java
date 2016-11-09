package cn.com.sky.threads.concurrent;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 * 
 * 
 *  非阻塞队列：
 * 
 * ConcurrentLinkedDeque，非阻塞双端队列，链表实现
 * 
 * ConcurrentLinkedQueue，非阻塞队列，链表实现
 * 
 * 
 * 在Java多线程应用中，队列的使用率很高，多数生产消费模型的首选数据结构就是队列(先进先出)。
 * Java提供的线程安全的Queue可以分为阻塞队列和非阻塞队列，
 * 其中阻塞队列的典型例子是BlockingQueue，
 * 非阻塞队列的典型例子是ConcurrentLinkedQueue，
 * 在实际应用中要根据实际需要选用阻塞队列或者非阻塞队列。
 * 
 */
public class TestConcurrentLinkedQueue {
	private final AtomicBoolean locked = new AtomicBoolean(false);
	private final ConcurrentLinkedQueue<Thread> waiters = new ConcurrentLinkedQueue<Thread>();

	public void lock() {
		boolean wasInterrupted = false;
		Thread current = Thread.currentThread();
		waiters.add(current);

		// Block while not first in queue or cannot acquire lock
		while (waiters.peek() != current || !locked.compareAndSet(false, true)) {
			LockSupport.park(this);
			if (Thread.interrupted()) // ignore interrupts while waiting
				wasInterrupted = true;
		}

		waiters.remove();
		if (wasInterrupted) // reassert interrupt status on exit
			current.interrupt();
	}

	public void unlock() {
		locked.set(false);
		LockSupport.unpark(waiters.peek());
	}
}