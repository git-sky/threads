package cn.com.sky.threads.concurrent.callablefuture;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestThreadPoolExecutor {

	/** 信号量 */
	private Semaphore semaphore = new Semaphore(0); // 1

	/** 线程池 */
	private ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 5, 3, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(3));

	
	private Future<String> future;

	public void test() {
		

		future = pool.submit(new Callable<String>() {
			@Override
			public String call() {
				String result = null;
				try {
					// 同步阻塞获取信号量
					semaphore.acquire();
					result = "ok";
				} catch (InterruptedException e) {
					result = "interrupted";
				}
				return result;
			}
		});

		String result = "timeout";

		// 等待3s
		try {
			result = future.get(3, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}

		// 删除线程池中任务
		boolean cancelResult = future.cancel(true);

		System.out.println("result is " + result);

		System.out.println("删除结果：" + cancelResult);

		System.out.println("当前active线程数：" + pool.getActiveCount());

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("当前active线程数：" + pool.getActiveCount());

	}

	public static void main(String[] args) {

		TestThreadPoolExecutor o = new TestThreadPoolExecutor();
		o.test();

	}

}