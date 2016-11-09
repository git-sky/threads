package cn.com.sky.threads.thread.terminal;

/**
 * 1.run方法执行结束,线程终止。
 */
public class ThreadNormal extends Thread {
	public volatile boolean exit = false;

	public void run() {
		while (!exit)
			System.out.println("i am running.........");;
	}

	public static void main(String[] args) throws Exception {
		ThreadNormal thread = new ThreadNormal();
		thread.start();
		sleep(5000); // 主线程延迟5秒
		thread.exit = true; // 终止线程thread
		thread.join();
		System.out.println("main线程退出!");
	}
}
