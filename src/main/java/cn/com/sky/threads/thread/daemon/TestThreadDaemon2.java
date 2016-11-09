package cn.com.sky.threads.thread.daemon;

/**
 * 守护线程
 */
public class TestThreadDaemon2 implements Runnable {
	int i = 0;

	public void run() {
		while (true) {
			System.out.println(Thread.currentThread().getName() + "在运行" + i++);
		}
	}

	public static void main(String[] args) {
		TestThreadDaemon2 he = new TestThreadDaemon2();
		Thread demo = new Thread(he, "线程");
		demo.setDaemon(true);
		demo.start();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}