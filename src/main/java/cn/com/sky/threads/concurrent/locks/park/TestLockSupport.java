package cn.com.sky.threads.concurrent.locks.park;

import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 * 
 * LockSupport是JDK中比较底层的类，用来创建锁和其他同步工具类的基本线程阻塞原语。
 * 
 * Java锁和同步器框架的核心 AQS:
 * AbstractQueuedSynchronizer，就是通过调用 LockSupport.park()和 LockSupport.unpark()实现线程的阻塞和唤醒 的。
 * LockSupport 很类似于二元信号量(只有1个许可证可供使用)，如果这个许可还没有被占用，当前线程获取许可并继 续 执行；如果许可已经被占用，当前线 程阻塞，等待获取许可。
 * 
 * 
 * LockSupport是不可重入 的，如果一个线程连续2次调用 LockSupport.park()，那么该线程一定会一直阻塞下去。
 * 
 */
public class TestLockSupport {
	public static void main(String[] args) {

//		 LockSupport.park();
//		 System.out.println("i am blocked...");
//		
//		 Thread thread = Thread.currentThread();
//		 LockSupport.unpark(thread);// 释放许可
//		 System.out.println("i am unpark...");
//		 LockSupport.unpark(thread);// 释放许可
//		 System.out.println("i am ok...");
//		 LockSupport.park(thread);// 获取许可
//		 System.out.println("i am ok...");

		try {
			test();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void test() throws Exception {

		Thread t = new Thread(new Runnable() {
			private int count = 0;

			@Override
			public void run() {
				long start = System.currentTimeMillis();
				long end = 0;

				while ((end - start) <= 1000) {
					count++;
					end = System.currentTimeMillis();
				}

				System.out.println("after 1 second.count=" + count);

				// 等待许可
				LockSupport.park();

				// 线程如果因为调用park而阻塞的话，能够响应中断请求(中断状态被设置成true)，但是不会抛出InterruptedException 。
				System.out.println("thread over..isInterrupted=" + Thread.currentThread().isInterrupted());

			}
		});

		t.start();

		Thread.sleep(2000);

		// 中断线程
		t.interrupt();

		System.out.println("main over");
	}

}
