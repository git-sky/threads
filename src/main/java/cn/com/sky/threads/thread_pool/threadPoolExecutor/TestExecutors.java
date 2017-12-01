package cn.com.sky.threads.thread_pool.threadPoolExecutor;

import cn.com.sky.threads.thread_pool.threadPoolExecutor.source.ExecutorService;
import cn.com.sky.threads.thread_pool.threadPoolExecutor.source.ThreadPoolExecutor;

import java.util.concurrent.Executors;

/**
 * <pre>
 * 
 * 线程池的执行流程：
 * 
 * 1.初始线程数是0；
 * 2.每来一个任务，新建一个线程，直到线程数达到 corePoolSize；
 * 3.继续来新任务，任务加入到队列（BlockingQueue）中，直到队列满；
 * 4.继续来新任务，新建一个线程，直到线程数达到 maximumPoolSize；
 * 5.继续来新任务，执行拒绝策略(RejectedExecutionHandler)。
 * 
 * 
 * Executors.newSingleThreadExecutor()
 * 可保证顺序地执行各个任务，并且在任意给定的时间不会有多个线程是活动的。
 * 
 * </pre>
 * 
 */
public class TestExecutors {

	public static void main(String[] args) {

		// 每来一个任务，就新建一个线程，当线程数是1的时候，再来任务就会放到队列LinkedBlockingQueue里面。
		ExecutorService single = (ExecutorService) Executors.newSingleThreadExecutor();
		// print(single);

		// 每来一个任务，就会新建一个线程。
//		 ExecutorService cache = Executors.newCachedThreadPool();
		// print(cache);

		// // 每来一个任务，就新建一个线程，当线程数是10的时候，再来任务就会放到队列LinkedBlockingQueue里面。
		ThreadPoolExecutor fixed = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

		// ThreadPoolExecutor fixed = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new
		// ArrayBlockingQueue<Runnable>(6));
		print(fixed);

	}

	private static void print(ThreadPoolExecutor pool) {
		for (int i = 0; i < 100; i++) {

			Runnable r = new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
							System.out.println(Thread.currentThread());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};

            pool.execute(r);
		}
	}
}
