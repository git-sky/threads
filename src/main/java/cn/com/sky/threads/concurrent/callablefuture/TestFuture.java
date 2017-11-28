package cn.com.sky.threads.concurrent.callablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <pre>
 * 
 * 
 * 在Future接口中声明了5个方法，下面依次解释每个方法的作用：
 * 
 * cancel() 方法用来取消任务，如果取消任务成功则返回true，如果取消任务失败则返回false。
 * 参数mayInterruptIfRunning表示是否允许取消正在执行却没有执行完毕的任务，如果设置true，则表示可以取消正在执行过程中的任务。
 * 如果任务已经完成，则无论mayInterruptIfRunning为true还是false，此方法肯定返回false，即如果取消已经完成的任务会返回false；
 * 如果任务正在执行，若mayInterruptIfRunning设置为true，则返回true，若mayInterruptIfRunning设置为false，则返回false；
 * 如果任务还没有执行，则无论mayInterruptIfRunning为true还是false，肯定返回true。
 * 
 * isCancelled() 方法表示任务是否被取消成功，如果在任务正常完成前被取消成功，则返回 true。
 *
 * isDone() 方法表示任务是否已经完成，若任务完成，则返回true；
 *
 * get() 方法用来获取执行结果，这个方法会产生阻塞，会一直等到任务执行完毕才返回；
 * 
 * get(long timeout, TimeUnit unit) 用来获取执行结果，如果在指定时间内，还没获取到结果，就直接返回null。
 * 
 * 
 * 也就是说Future提供了三种功能：
 * 
 * 1）判断任务是否完成；
 * 
 * 2）能够中断任务；
 * 
 * 3）能够获取任务执行结果。
 * 
 * 因为Future只是一个接口，所以是无法直接用来创建对象使用的，因此就有了FutureTask。
 * 
 * 
 * submit方便Exception处理
 * 
 * Future通过get方法获取执行结果，该方法会阻塞直到任务返回结果。
 * 
 * 比如说，我有很多更新各种数据的task，我希望如果其中一个task失败，其它的task就不需要执行了。
 * 那我就需要catch Future.get抛出的异常，然后终止其它task的执行。
 * 
 * </pre>
 */
public class TestFuture {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newCachedThreadPool();
		List<Future<String>> resultList = new ArrayList<Future<String>>();

		// 创建10个任务并执行
		for (int i = 0; i < 10; i++) {
			// 使用ExecutorService执行Callable类型的任务，并将结果保存在future变量中
			// Future future = threadPool.submit(new TaskRun(i));
			Future future = threadPool.submit(new TaskCallable(i));
			// 将任务执行结果存储到List中
			resultList.add(future);
		}
		threadPool.shutdown();

		// 遍历任务的结果
		for (Future<String> fs : resultList) {
			try {
				System.out.println(fs.get()); // 打印各个线程（任务）执行的结果
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				threadPool.shutdownNow();
				e.printStackTrace();
				return;
			}
		}
	}
}

class TaskCallable implements Callable {

	private int id;

	public TaskCallable(int id) {
		this.id = id;
	}

	/**
	 * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
	 */
	public Object call() throws Exception {
		System.out.println("call()方法被自动调用,干活！！！             " + Thread.currentThread().getName());
		if (new Random().nextBoolean())
			throw new TaskException("Meet error in task." + Thread.currentThread().getName());
		// 一个模拟耗时的操作
		for (int i = 999999999; i > 0; i--)
			;
		return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
	}
}

class TaskException extends Exception {
	private static final long serialVersionUID = 1L;

	public TaskException(String message) {
		super(message);
	}
}

class TaskRun implements Runnable {

	private int id;

	public TaskRun(int id) {
		this.id = id;
	}

	/**
	 * 任务的具体过程，一旦任务传给ExecutorService的submit方法，则该方法自动在一个线程上执行。
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName() + " id=" + id + "  run()... ");
		if (new Random().nextBoolean())
			// 一个模拟耗时的操作
			for (int i = 999999999; i > 0; i--)
				;
	}
}