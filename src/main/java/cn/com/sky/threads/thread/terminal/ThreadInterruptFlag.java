package cn.com.sky.threads.thread.terminal;

/**
 * 根据中断状态，结束线程。
 */
public class ThreadInterruptFlag extends Thread {

	public void run() {
		int i = 0;
		while (!Thread.currentThread().isInterrupted()) {
			System.out.println(i++);
		}

		// isInterrupted不会清除中断状态,检测中断状态（ private native boolean isInterrupted(false);）
		System.out.println("isInterrupted:" + Thread.currentThread().isInterrupted());
		System.out.println("isInterrupted:" + Thread.currentThread().isInterrupted());

		// interrupted方法会清除中断状态，检测中断状态（ private native boolean isInterrupted(true);）
		System.out.println("interrupted:" + Thread.interrupted());// true
		System.out.println("interrupted:" + Thread.interrupted());// false

		Thread.currentThread().interrupt();//执行线程interrupt事件

		// isInterrupted不会清除中断状态,检测中断状态（ private native boolean isInterrupted(false);）
		System.out.println("isInterrupted:" + Thread.currentThread().isInterrupted());
		System.out.println("isInterrupted:" + Thread.currentThread().isInterrupted());

		// interrupted方法会清除中断状态，检测中断状态（ private native boolean isInterrupted(true);）
		System.out.println("interrupted:" + Thread.interrupted());// true
		System.out.println("interrupted:" + Thread.interrupted());// false


	}

	public static void main(String[] args) throws Exception {
		Thread t = new ThreadInterruptFlag();
		t.start();
		System.out.println("在按任意键中断线程!");
		System.in.read();
		t.interrupt();
		t.join();
		System.out.println("线程已经退出!");
	}
}
