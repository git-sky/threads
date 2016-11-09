package cn.com.sky.threads.concurrent.callablefuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <pre>
 * 
 * FutureTask是一个可取消的异步计算（A cancellable asynchronous computation.）
 * 
 * FutureTask可处于等待运行、正在运行和运行完成这3种状态，运行完成包括所有可能的结束方式：正常结束、由于取消而结束和异常运行结束，FutureTask进入运行完成状态后，将永远处于该状态。
 * 
 * 示例一：FutureTask的简单示例（调用FutureTask的run方法使其开始运行，也可将其封装至Thread中通过调用Thread的start方法间接调用FutureTask的run方法
 * ）
 *
 */
public class TestFutureTaskCancel {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		TestFutureTaskCancel task = new TestFutureTaskCancel();
		// 如果运行任务时任务处于运行结束状态则会报线程状态异常：
		// java.lang.IllegalThreadStateException
		if (!task.isDone()) {
			task.run();
		}
		Thread.sleep(1000);
		// 此时任务已经完成，故取消失败
		System.out.println(task.cancel());
		System.out.println("result:" + task.get());
	}

	private MyFutureTask<Integer> ft = new MyFutureTask<Integer>(new Callable<Integer>() {
		public Integer call() throws Exception {
//			Thread.sleep(3000);
			return new Integer(0);
		};
	});

	private Thread t = new Thread(ft);

	public void run() {
		t.start();
	}

	public Integer get() throws InterruptedException, ExecutionException {
		// get()方法的运行方式取决于任务的状态
		// 如果任务已经完成，则直接返回结果
		// 如果任务正在运行中，则阻塞直到任务完成
		return ft.get();
	}

	public boolean cancel() {
		// 尝试取消任务，如果任务处于运行结束状态或由于其它某些原因
		// 无法取消，则尝试失败
		return ft.cancel(true);
	}

	public boolean isDone() {
		return ft.isDone();
	}

}

final class MyFutureTask<V> extends FutureTask<V> {
	
	public MyFutureTask(Callable<V> callable) {
		super(callable);
	}

	// 当任务进入运行结束状态，无论是正常结束、取消任务还是异常结束
	// 都将会调用该方法
	@Override
	protected void done() {
		if (this.isDone()) {
			System.out.println("MyFutureTask is over!");
		}
	}
}
