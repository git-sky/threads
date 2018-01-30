package cn.com.sky.threads.concurrent.tools.phaser;

import java.util.concurrent.Phaser;

public class TestPhaser6 {
	public static void main(String[] args) {
		
		Phaser phaser = new Phaser(1); // 相当于CountDownLatch(1)

		// 五个子任务
		for (int i = 0; i < 3; i++) {
			Tasks task = new Tasks(phaser);
			Thread thread = new Thread(task, "PhaseTest_" + i);
			thread.start();
		}

		try {
			// 等待3秒
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		phaser.arrive(); // countDownLatch.countDown()
	}

	static class Tasks implements Runnable {
		private final Phaser phaser;

		Tasks(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {
			phaser.awaitAdvance(phaser.getPhase()); // countDownLatch.await()
			System.out.println(Thread.currentThread().getName() + "执行任务...");

		}
	}
}