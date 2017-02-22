package cn.com.sky.threads.thread.terminal;

/**
 * 中断wait方法，结束线程。
 */
public class ThreadInterruptWait extends Thread {

	public synchronized void run() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		Thread t = new ThreadInterruptWait();
		t.start();
		System.out.println("在按任意键中断线程!");
		System.in.read();
		t.interrupt();
		t.join();
		System.out.println("线程已经退出!");
	}
}
