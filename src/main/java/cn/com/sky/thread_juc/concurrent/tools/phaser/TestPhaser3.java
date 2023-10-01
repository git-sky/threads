package cn.com.sky.thread_juc.concurrent.tools.phaser;

import java.util.Random;
import java.util.concurrent.Phaser;

/**
 * <pre>
 * 
 *  Arrival
 *  
 *  每个Phaser实例都会维护一个phase number，初始值为0。
 *  每当所有注册的任务都到达Phaser时，phase number累加，并在超过Integer.MAX_VALUE后清零。
 *  arrive()和arriveAndDeregister()方法用于记录到达；
 *  arrive()，某个参与者完成任务后调用。
 *  arriveAndDeregister()，任务完成，取消自己的注册。
 *  arriveAndAwaitAdvance()，自己完成等待其他参与者完成，进入阻塞，直到Phaser成功进入下个阶段。
 * 
 * 
 * 
 * 所有子线程的****+”继续执行任务…”，都是在线程调用arriveAndAwaitAdvance()方法之后执行的。
 *
 * </pre>
 */
public class TestPhaser3 {

	public static void main(String[] args) {

		Phaser phaser = new Phaser(5);

		for (int i = 0; i < 5; i++) {
			Task_01 task_01 = new Task_01(phaser);
			Thread thread = new Thread(task_01, "PhaseTest_" + i);
			thread.start();
		}
	}

	static class Task_01 implements Runnable {
		private final Phaser phaser;

		public Task_01(Phaser phaser) {
			this.phaser = phaser;
		}

		@Override
		public void run() {

            try {
                Thread.sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "执行任务完成，等待其他任务执行......");
			// 等待其他任务执行完成
			phaser.arriveAndAwaitAdvance();
			System.out.println(Thread.currentThread().getName() + "继续执行任务...");
            try {
                Thread.sleep(new Random().nextInt(5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 等待其他任务执行完成
            phaser.arriveAndAwaitAdvance();

            System.out.println(Thread.currentThread().getName() + "继续执行任务 2...");

        }
	}
}