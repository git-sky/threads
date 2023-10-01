package cn.com.sky.threads.concurrent.callablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <pre>
 * 
 * RunnableFuture继承了Runnable接口和Future接口，而FutureTask实现了RunnableFuture接口。
 * 所以它既可以作为Runnable被线程执行， 又可以作为Future得到Callable的返回值。
 * 
 * FutureTask(Callable callable)
 *
 */
public class TestFutureTask {

	public static void main(String args[]) {

		WorkerCallable worker = new WorkerCallable();
		FutureTask<Integer> ftask = new FutureTask<>(worker);
		new Thread(ftask).start();
		

		while (!ftask.isDone()) {
			try {
				System.out.println("is ok?...");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int amount;
		try {
			amount = ftask.get();
			System.out.println("had worked...amount=" + amount);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

	}

	static class WorkerCallable implements Callable<Integer> {

		private int hours = 12;
		private int amount;

		@Override
		public Integer call() throws Exception {
			while (hours > 0) {
				System.out.println("I'm working......");
				amount++;
				hours--;
				Thread.sleep(1000);
			}
			return amount;
		}
	}
}