package cn.com.sky.threads.thread.synchronize;

/**
 * 2个线程持有同一个对象锁（TestThreadSyncInMethod2）
 * 
 * 同步代码块
 */
public class TestThreadSyncInMethod2 implements Runnable {
	public void run() {
		synchronized (this) {
			for (int i = 0; i < 500000; i++) {
				System.out.println(Thread.currentThread().getName() + " synchronized loop " + i);
			}
		}
	}

	public static void main(String[] args) {
		TestThreadSyncInMethod2 t1 = new TestThreadSyncInMethod2();
		Thread ta = new Thread(t1, "A");
		Thread tb = new Thread(t1, "B");
		ta.start();
		tb.start();
	}
}