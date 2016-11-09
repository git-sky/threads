package cn.com.sky.threads.concurrent.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * <pre>
 * 
 * "如果向Executor提交了一组计算任务，并且希望在计算完成后获得结果，那么可以保留与每个任务关联的Future，然后反复使用get方法，同时将参数timeout指定为0，从而通过轮询来判断任务是否完成。
 * 这种方法虽然可行，但却有些繁琐。幸运的是，还有一种更好的方法：完成服务CompletionService。"
 * 
 * 1.繁琐的做法
 * 
 * 通过一个List来保存每个任务返回的Future，然后轮询这些Future，直到每个Future都已完成。
 * 我们不希望出现因为排在前面的任务阻塞导致后面先完成的任务的结果没有及时获取的情况，
 * 所以在调用get方式时，需要将超时时间设置为0。
 * 
 * 可见轮询future列表非常的复杂，而且还有很多异常需要处理，TimeOutException异常需要忽略；
 * 还要通过双重循环和break，防止遍历集合的过程中，出现并发修改异常。
 * 这么多需要考虑的细节，程序员很容易犯错。
 *
 */
public class TestTraditional {
	public static void main(String[] args) {
		int taskSize = 5;

		ExecutorService executor = Executors.newFixedThreadPool(taskSize);

		List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();

		for (int i = 1; i <= taskSize; i++) {
			int sleep = taskSize - i; // 睡眠时间

			int value = i; // 返回结果

			// 向线程池提交任务
			Future<Integer> future = executor.submit(new ReturnAfterSleepCallable(sleep, value));

			// 保留每个任务的Future
			futureList.add(future);
		}

		// 轮询,获取完成任务的返回结果
		while (taskSize > 0) {
			for (Future<Integer> future : futureList) {
				Integer result = null;

				try {
					System.out.println("future.get() before...........");
					result = future.get(0, TimeUnit.SECONDS);
					System.out.println("future.get() after................");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				} catch (TimeoutException e) {
					// 超时异常需要忽略,因为我们设置了等待时间为0,只要任务没有完成,就会报该异常
				}

				// 任务已经完成
				if (result != null) {
					System.out.println("result=" + result);

					// 从future列表中删除已经完成的任务
					futureList.remove(future);
					taskSize--;
					// 此处必须break，否则会抛出并发修改异常。（也可以通过将futureList声明为CopyOnWriteArrayList类型解决）
					break; // 进行下一次while循环
				}
			}
		}

		// 所有任务已经完成,关闭线程池
		System.out.println("all over.");
		executor.shutdown();
	}

}