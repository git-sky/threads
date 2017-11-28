package cn.com.sky.threads.thread.terminal;

/**
 * <pre>
 * 
 * 使用stop终止线程,已经弃用。
 * 当线程要终止另一个线程时，无法知道什么时候调用stop方法是安全的，什么时候导致对象被破坏。
 * 因此，该stop方法被弃用了。
 * 
 * </pre>
 */
public class ThreadStop extends Thread {

	public void run() {
		while (true) {
			System.out.println("i am running.........");
		}
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws Exception {
		ThreadStop thread = new ThreadStop();
		thread.start();
		sleep(5000); // 主线程延迟5秒
		thread.stop();
		thread.join();
		System.out.println("main线程退出!");
	}
}
