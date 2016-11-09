package cn.com.sky.threads.concurrent.callablefuture;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
 * <pre>
 * 
 * Future f = submit(Runnable task)
 * f.get返回null
 * 
 * 当将一个Callable的对象传递给ExecutorService的submit方法，则该call方法自动在一个线程上执行，并且会返回执行结果Future对象。
 * 同样，将Runnable的对象传递给ExecutorService的submit方法，则该run方法自动在一个线程上执行，并且会返回执行结果Future对象，但是在该Future对象上调用get方法，将返回null。
 *
 * </pre>
 *
 */
public class TestCallable {

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService exec = Executors.newCachedThreadPool();

		// Future相当于是用来存放Executor执行的结果的一种容器
		ArrayList<Future<?>> results = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
//			 results.add(exec.submit(new TaskCallable(i)));
			results.add(exec.submit(new TaskRunnable(i),"aaaaaaa"));
		}

		for (Future fs : results) {
			if (fs.isDone()) {
				System.out.println("completed: " + fs.get());
			} else {
				System.out.println("Future result is not yet complete: " + fs.get());
			}
		}
		exec.shutdown();
	}

	static class TaskCallable implements Callable {

		private int id;

		public TaskCallable(int id) {
			this.id = id;
		}

		@Override
		public Object call() throws Exception {
			return "result=" + id;
		}
	}

	static class TaskRunnable implements Runnable {

		private int id;

		public TaskRunnable(int id) {
			this.id = id;
		}

		@Override
		public void run() {
//			System.out.println("run........");
		}
	}

}
