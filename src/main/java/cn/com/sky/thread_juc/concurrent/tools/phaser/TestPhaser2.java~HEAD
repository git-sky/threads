package cn.com.sky.thread_juc.concurrent.tools.phaser;

import java.util.concurrent.Phaser;

/**
 * <pre>
 * 
 * 
 * CyclicBarrier，允许一组线程互相等待，直到到达某个公共屏障点。它提供的await()可以实现让所有参与者在临界点到来之前一直处于等待状态。
 * 
 * CountDownLatch，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。它提供了await()、countDown()两个方法来进行操作。
 * 
 * 在Phaser中，它把多个线程协作执行的任务划分为多个阶段，编程时需要明确各个阶段的任务，每个阶段都可以有任意个参与者，线程都可以随时注册并参与到某个阶段。
 * 
 * 
 * </pre>
 */
public class TestPhaser2 {

	public static void main(String[] args) {
		Phaser phaser = new Phaser(3) {// 共有3个工作线程，因此在构造函数中赋值为3
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println("\n=========华丽的分割线=============");
				// 本例中，当只剩一个线程时，这个线程必定是主线程，返回true表示终结
				return registeredParties == 1;
			}
		};
		System.out.println("程序开始执行");
		for (int i = 0; i < 3; i++) { // 创建并启动3个线程
			new MyThread2((char) (97 + i), phaser).start();
		}

		phaser.register(); // 将主线程动态增加到phaser中，此句执行后phaser共管理4个线程
		while (!phaser.isTerminated()) {// 只要phaser不终结，主线程就循环等待
			int n = phaser.arriveAndAwaitAdvance();
		}
		// 跳出上面循环后，意味着phaser终结，即3个工作线程已经结束
		System.out.println("程序结束");
	}
}

class MyThread2 extends Thread {
	private char c;
	private Phaser phaser;

	public MyThread2(char c, Phaser phaser) {
		this.c = c;
		this.phaser = phaser;
	}

	@Override
	public void run() {
		while (!phaser.isTerminated()) {
			for (int i = 0; i < 10; i++) { // 将当前字母打印10次
				System.out.print(c + " ");
			}
			// 打印完当前字母后，将其更新为其后第三个字母，例如b更新为e，用于下一阶段打印
			c = (char) (c + 3);
			if (c > 'z') {
				// 如果超出了字母z，则在phaser中动态减少一个线程，并退出循环结束本线程
				// 当3个工作线程都执行此语句后，phaser中就只剩一个主线程了
				phaser.arriveAndDeregister();
				break;
			} else {
				// 反之，等待其他线程到达阶段终点，再一起进入下一个阶段
				phaser.arriveAndAwaitAdvance();
			}
		}
	}
}