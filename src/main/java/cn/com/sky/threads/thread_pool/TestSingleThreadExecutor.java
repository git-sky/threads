package cn.com.sky.threads.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <pre>
 * jdk:
 * Unlike the otherwise equivalent newFixedThreadPool(1) the returned executor is guaranteed not to be reconfigurable to use additional threads.
 * 
 * 
 * The difference is (only) that the SingleThreadExecutor cannot have his thread size adjusted later on, 
 * which you can do with a FixedThreadExecutor by calling ThreadPoolExecutor#setCorePoolSize (needs a cast first).
 * 
 * 区别：
 * Executors.newSingleThreadExecutor()可以保证顺序执行任务，并且线程数是不可以再配置的。
 * Executors.newFixedThreadPool(1)可以保证顺序执行任务，线程数可以再进行配置，((ThreadPoolExecutor) fixedThreadPool).setMaximumPoolSize(10);
 * 
 * 
 * 
 * 有界队列:
 * 当使用有限的 maximumPoolSizes 时，有界队列（如 ArrayBlockingQueue）有助于防止资源耗尽，但是可能较难调整和控制。
 * 队列大小和最大池大小可能需要相互折衷：使用大型队列和小型池可以最大限度地降低 CPU 使用率、操作系统资源和上下文切换开销，但是可能导致人工降低吞吐量。
 * 如果任务频繁阻塞（例如，如果它们是 I/O 边界），则系统可能为超过您许可的更多线程安排时间。
 * 使用小型队列通常要求较大的池大小，CPU 使用率较高，但是可能遇到不可接受的调度开销，这样也会降低吞吐量。
 * 
 * </pre>
 */
public class TestSingleThreadExecutor extends Thread {
	private int index;

	public TestSingleThreadExecutor(int i) {
		this.index = i;
	}

	public void run() {
		try {
			System.out.println("[" + this.index + "] start....");
			// Thread.sleep((int) (Math.random() * 1000));
			System.out.println("[" + this.index + "] end.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(1);

		for (int i = 0; i < 20; i++) {
			fixedThreadPool.execute(new TestSingleThreadExecutor(i));
		}

		// it's not a single thread pool any more,it will reconfigure the existing one to allow more
		// than one thread.
		((ThreadPoolExecutor) fixedThreadPool).setCorePoolSize(10);
		((ThreadPoolExecutor) fixedThreadPool).setMaximumPoolSize(10);

		System.out.println("submit finish");
		fixedThreadPool.shutdown();

		ExecutorService single = Executors.newSingleThreadExecutor();
		((ThreadPoolExecutor) single).setCorePoolSize(10);// 类转换错误
	}

}