package cn.com.sky.threads.concurrent.callablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 
 * submit(Runnable task)--->new FutureTask(Runnable task,T value)--->new Sync(Executors.callable(runnable, result))-->
 * new RunnableAdapter<T>(task, result)-->task.run();
 * 
 * submit(Callable task)--->new FutureTask<T>(callable)---> new Sync(callable)
 * 
 * execute(Runnable command)
 */
public class TestFutureTask2 {

	public static void main(String[] args) {
		// 第一种方式
		// ExecutorService executor = Executors.newCachedThreadPool();

		ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		TaskCallable task = new TaskCallable();
		FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
		executor.submit(futureTask);
//		 executor.execute(futureTask);
		executor.shutdown();

		// 第二种方式，注意这种方式和第一种方式效果是类似的，只不过一个使用的是ExecutorService，一个使用的是Thread
		// TaskCallable task = new TaskCallable();
		// FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
		// Thread thread = new Thread(futureTask);
		// thread.start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		System.out.println("主线程在执行任务");

		try {
			System.out.println("task运行结果" + futureTask.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		System.out.println("所有任务执行完毕");
	}

	static class TaskCallable implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			System.out.println("子线程在进行计算");
			Thread.sleep(3000);
			int sum = 0;
			for (int i = 0; i < 100; i++)
				sum += i;
			return sum;
		}
	}
}
