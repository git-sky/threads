package cn.com.sky.threads.thread.thread_local;

public class InheritableThreadLocalTest {

	private static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<Integer>();

	public static void main(String[] args) throws InterruptedException {

		threadLocal.set(23);

		System.out.println(Thread.currentThread() + ",ThreadLocal Value=" + threadLocal.get());
		Thread tt = new Thread("wendy") {
			@Override
			public void run() {
				System.out.println(Thread.currentThread() + ",ThreadLocal Value=" + threadLocal.get());
				threadLocal.set(28);
				System.out.println(Thread.currentThread() + ",ThreadLocal Value=" + threadLocal.get());
			}
		};

		tt.start();

		Thread.sleep(1000);

		System.out.println(Thread.currentThread() + ",ThreadLocal Value=" + threadLocal.get());

	}
}
