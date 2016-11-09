package cn.com.sky.threads.thread.terminal;

public class ThreadException extends Thread {
	public volatile boolean exit = false;

	public void run() {
		while (!exit) {
			System.out.println("i am running.........");
			int zero = 0;
			int a = 10 / zero;
			System.out.println(a);
		}
	}

	public static void main(String[] args) throws Exception {
		ThreadException thread = new ThreadException();
		thread.start();
		sleep(5000); // 主线程延迟5秒
		thread.join();// 等待thread执行完毕，然后主线程继续向下执行。
		System.out.println("main线程退出!");
	}
}
