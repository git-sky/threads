package cn.com.sky.threads.concurrent.completionservice;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>
 * 
 * 如果你向Executor提交了一个批处理任务，并且希望在它们完成后获得结果。为此你可以保存与每个任务相关联的Future，然后不断地调用timeout为零的get，来检验Future是否完成。
 * 这样做固然可以，但却相当乏味。幸运的是，还有一个更好的方法：完成服务(Completion service)。
 * 
 * CompletionService整合了Executor和BlockingQueue的功能。
 * 你可以将Callable任务提交给它去执行，然后使用类似于队列中的take和poll方法， 在结果完整可用时获得这个结果，像一个打包的Future。
 * ExecutorCompletionService是实现CompletionService接口的一个类，并将计算任务委托给一个Executor。
 * 
 * ExecutorCompletionService的实现相当直观。
 * 它在构造函数中创建一个BlockingQueue，用它去保持完成的结果。计算完成时会调用FutureTask中的done方法。
 * 当提交一个任务后，首先把这个任务包装为一个QueueingFuture，它是FutureTask的一个子类，然后覆写done方法，将结果置入BlockingQueue中，
 * take和poll方法委托给了BlockingQueue，它会在结果不可用时阻塞。
 *
 */
public class TestCompletionService3 {
	public static void main(String[] args) throws Exception {
		TestCompletionService3 t = new TestCompletionService3();
		t.count1();
		t.count2();
	}

	// 使用阻塞容器保存每次Executor处理的结果，在后面进行统一处理
	public void count1() throws Exception {
		ExecutorService exec = Executors.newCachedThreadPool();
		BlockingQueue<Future<Integer>> queue = new LinkedBlockingQueue<Future<Integer>>();
		for (int i = 0; i < 10; i++) {
			Future<Integer> future = exec.submit(getTask());
			queue.add(future);
		}
		int sum = 0;
		int queueSize = queue.size();
		for (int i = 0; i < queueSize; i++) {
			sum += queue.take().get();
		}
		System.out.println("总数为：" + sum);
		exec.shutdown();
	}

	// 使用CompletionService(完成服务)保持Executor处理的结果
	public void count2() throws InterruptedException, ExecutionException {
		ExecutorService exec = Executors.newCachedThreadPool();
		CompletionService<Integer> execcomp = new ExecutorCompletionService<Integer>(exec);
		for (int i = 0; i < 10; i++) {
			execcomp.submit(getTask());
		}
		int sum = 0;
		for (int i = 0; i < 10; i++) {
			// 检索并移除表示下一个已完成任务的 Future，如果目前不存在这样的任务，则等待。
			Future<Integer> future = execcomp.take();
			sum += future.get();
		}
		System.out.println("总数为：" + sum);
		exec.shutdown();
	}

	// 得到一个任务
	public Callable<Integer> getTask() {
		final Random rand = new Random();
		Callable<Integer> task = new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				int i = rand.nextInt(10);
				int j = rand.nextInt(10);
				int sum = i * j;
				System.out.print(sum + "\t");
				return sum;
			}
		};
		return task;
	}
}