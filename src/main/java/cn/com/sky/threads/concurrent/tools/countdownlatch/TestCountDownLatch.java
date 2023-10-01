package cn.com.sky.threads.concurrent.tools.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch:闭锁,减法器
 * 
 * 组合AQS实现
 * await时，如果state不为0，则线程入队列。
 * countDown时，执行state-1操作，当state为0时，唤醒后继线程。
 */
public class TestCountDownLatch {
	public static void main(String[] args) throws InterruptedException {
		// 开始的倒数锁
		final CountDownLatch begin = new CountDownLatch(1);
		// 结束的倒数锁
		final CountDownLatch end = new CountDownLatch(10);// value=10;
		// 十名选手
		final ExecutorService exec = Executors.newFixedThreadPool(10);

		for (int index = 0; index < 10; index++) {
			final int NO = index + 1;
			Runnable run = new Runnable() {
				public void run() {
					try {
						begin.await();// 当state不为0时，阻塞
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println("No." + NO + " arrived");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						end.countDown();// value=value-1,当value=0时，unpark下一个节点
					}
				}
			};
			exec.submit(run);
		}
		System.out.println("Game Start");
		begin.countDown();
		end.await();// 当value!=0时，park阻塞，当value=0时，unpark恢复
		System.out.println("Game Over");
		exec.shutdown();
	}
}