package cn.com.sky.threads.thread.other_method;

/**
 * <pre>
 * 
 * 判断线程是否启动
 * 
 * isAlive()返回true表示这个线程的run()方法还在运行
 * 
 * 当调用start方法后，线程开始执行run方法中的代码。线程进入运行状态。可以通过Thread类的isAlive方法来判断线程是否处于运行状态。
 * 当线程处于运行状态时，isAlive返回true，
 * 当isAlive返回false时，可能线程处于等待状态，也可能处于停止状态。
 * 
 * */
public class TestThreadIsAlive implements Runnable {
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName());
		}
	}

	public static void main(String[] args) {
		TestThreadIsAlive he = new TestThreadIsAlive();
		Thread demo = new Thread(he);
		System.out.println("线程启动之前---》" + demo.isAlive());
		demo.start();
		System.out.println("线程启动之后---》" + demo.isAlive());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("线程启动之后---》" + demo.isAlive());
	}
}