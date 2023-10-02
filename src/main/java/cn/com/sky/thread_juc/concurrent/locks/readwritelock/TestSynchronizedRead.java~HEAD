package cn.com.sky.thread_juc.concurrent.locks.readwritelock;

public class TestSynchronizedRead {

	public static void main(String[] args) {
		
		final TestSynchronizedRead test = new TestSynchronizedRead();

		new Thread() {
			public void run() {
				test.get(Thread.currentThread());
			};
		}.start();

		new Thread() {
			public void run() {
				test.get(Thread.currentThread());
			};
		}.start();

	}

	public synchronized void get(Thread thread) {
		System.out.println(thread.getName() + "正在进行读操作");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(thread.getName() + "读操作完毕");
	}
}