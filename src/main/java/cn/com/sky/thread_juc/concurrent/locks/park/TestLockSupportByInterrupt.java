package cn.com.sky.thread_juc.concurrent.locks.park;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的park和Object的wait一样也能响应中断。
 * 但是park它不会抛出InterruptedException。
 */
public class TestLockSupportByInterrupt {

	public static void main(String[] args) throws InterruptedException {

		final Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				LockSupport.park();
				System.out.println(Thread.currentThread() + " awake!");
			}
		});
		t.setName("t1");
		t.start();
		Thread.sleep(3000);

		// 2. 中断
		t.interrupt();
		System.out.println(t.isInterrupted());
	}
}