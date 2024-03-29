package cn.com.sky.thread_juc.concurrent.locks.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestCondition2 {

	/**
	 * BoundedBuffer 是一个定长100的集合，当集合中没有元素时，take方法需要等待，直到有元素时才返回元素
	 * 当其中的元素数达到最大值时，要等待直到元素被take之后才执行put的操作.
	 * 
	 */
	static class BoundedBuffer {
		final Lock lock = new ReentrantLock();
		final Condition notFull = lock.newCondition();
		final Condition notEmpty = lock.newCondition();

		final Object[] items = new Object[100];
		int putptr, takeptr, count;

		public void put(Object x) throws InterruptedException {
			System.out.println("put wait lock");
			lock.lock();
			System.out.println("put get lock");
			try {
				while (count == items.length) {
					System.out.println("buffer full, please wait");
					notFull.await();
				}

				items[putptr] = x;
				if (++putptr == items.length)
					putptr = 0;
				++count;
				notEmpty.signal();
			} finally {
				lock.unlock();
			}
		}

		public Object take() throws InterruptedException {
			System.out.println("take wait lock");
			lock.lock();
			System.out.println("take get lock");
			try {
				while (count == 0) {
					System.out.println("no elements, please wait");
					notEmpty.await();
				}
				Object x = items[takeptr];
				if (++takeptr == items.length)
					takeptr = 0;
				--count;
				notFull.signal();
				return x;
			} finally {
				lock.unlock();
			}
		}
	}

	public static void main(String[] args) {
		final BoundedBuffer boundedBuffer = new BoundedBuffer();

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					try {
						boundedBuffer.put(Integer.valueOf(i));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 1000; i++) {
					try {
						Object val = boundedBuffer.take();
						System.out.println(val);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		});

		t1.start();
		t2.start();
	}
}
