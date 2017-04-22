package cn.com.sky.threads.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TestExecutors {

	public static void main(String[] args) {

		// 每来一个任务，就新建一个线程，当线程数是1的时候，再来任务就会放到队列LinkedBlockingQueue里面。
		// ExecutorService single = Executors.newSingleThreadExecutor();
		// print(single);

		// 每来一个任务，就会新建一个线程。
		// ExecutorService cache = Executors.newCachedThreadPool();
		// print(cache);

		// // 每来一个任务，就新建一个线程，当线程数是10的时候，再来任务就会放到队列LinkedBlockingQueue里面。
		// ThreadPoolExecutor fixed = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

		ThreadPoolExecutor fixed = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(6));
		print(fixed);

	}

	private static void print(ThreadPoolExecutor single) {
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

			single.execute(r);
		}
	}
}
