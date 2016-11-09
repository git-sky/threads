package cn.com.sky.threads.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.junit.Test;

/**
 * <pre>
 * 
 * ReentrantLock的实现不仅可以替代隐式的synchronized关键字，而且能够提供超过关键字本身的多种功能。
 * 这里提到一个锁获取的公平性问题，如果在绝对时间上，先对锁进行获取的请求一定被先满足 ，那么这个锁是公平的，反之，是不公平的，也就是说等待时间最长的线程最有机会获取锁，也可以说锁的获取是有序的。
 * ReentrantLock这个锁提供了一个构造函数，能够控制这个锁是否是公平的。
 * 而锁的名字也是说明了这个锁具备了重复进入的可能，也就是说能够让当前线程多次的进行对锁的获取操作，这样的最大次数限制是Integer.MAX_VALUE，约21亿次左右。
 * 事实上公平的锁机制往往没有非公平的效率高 ，因为公平的获取锁没有考虑到操作系统对线程的调度因素，这样造成JVM对于等待中的线程调度次序和操作系统对线程的调度之间的不匹配。
 * 对于锁的快速且重复的获取过程中 ，连续获取的概率是非常高的，而公平锁会压制这种情况 ，虽然公平性得以保障，但是响应比却下降了，但是并不是任何场景都是以TPS作为唯一指标的，因为公平锁能够减少“饥饿”发生的概率，等待越久的请求越是能够得到优先满足。
 * 
 * 非公平的结果一个线程连续获取锁的情况非常多，而公平的结果连续获取的情况比较少。
 * 
 * </pre>
 *
 */
public class TestReentrantLockFairUnFair {
	private static Lock fairLock = new ReentrantLock(true);
	private static Lock unfairLock = new ReentrantLock();

	@Test
	public void fair() {
		System.out.println("fair version");
		for (int i = 0; i < 50; i++) {
			Thread thread = new Thread(new Job(fairLock));
			thread.setName("" + i);
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void unfair() {
		System.out.println("unfair version");
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(new Job(unfairLock));
			thread.setName("" + i);
			thread.start();
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static class Job implements Runnable {
		private Lock lock;

		public Job(Lock lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			for (int i = 0; i < 50; i++) {
				lock.lock();
				try {
					System.out.println("Lock by:" + Thread.currentThread().getName());
				} finally {
					lock.unlock();
				}
			}
		}
	}
}