package cn.com.sky.threads;

/**
 * <pre>
 * 
 * Runtime.getRuntime().addShutdownHook(shutdownHook);
 *
 * 这个方法的意思就是在jvm中增加一个关闭的钩子，当jvm关闭的时候，会执行系统中已经设置的所有通过方法addShutdownHook添加的钩子，
 *
 * 当系统执行完这些钩子后，jvm才会关闭。所以这些钩子可以在jvm关闭的时候进行内存清理、对象销毁等操作。
 * 
 */
public class TestShutdownHook {

	public static void main(String[] args) {
		Thread thread1 = new Thread() {
			public void run() {
				System.out.println("thread1...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("thread1 sleep over...");
			}
		};

		Thread thread2 = new Thread() {
			public void run() {
				System.out.println("thread2...");
			}
		};

		Thread shutdownThread = new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("shutdownThread...");
			}
		};

		Runtime.getRuntime().addShutdownHook(shutdownThread);

		System.exit(0);//jvm退出


		thread1.start();

		 System.exit(0);//jvm退出

		thread2.start();
	}
}
