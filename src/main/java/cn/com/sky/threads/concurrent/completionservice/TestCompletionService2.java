package cn.com.sky.threads.concurrent.completionservice;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 
 * 2.使用CompletionService
 * 
 * 可见使用CompletionService不会有TimeOutExeception的问题，不用遍历future列表，不用担心并发修改异常。
 * 
 */
public class TestCompletionService2 {
	public static void main(String[] args) {

		int taskSize = 5;

		ExecutorService executor = Executors.newFixedThreadPool(taskSize);

		// 构建完成服务
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);

		for (int i = 1; i <= taskSize; i++) {
			int sleep = taskSize - i; // 睡眠时间

			int value = i; // 返回结果

			// 向线程池提交任务
			completionService.submit(new ReturnAfterSleepCallable(sleep, value));
		}

		// 按照完成顺序,打印结果
		for (int i = 0; i < taskSize; i++) {
			try {
				System.out.println(completionService.take().get());

				System.out.println("i===" + i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		// 所有任务已经完成,关闭线程池
		System.out.println("all over.");
		executor.shutdown();
	}

    private static class ReturnAfterSleepCallable implements Callable<Integer> {

        private int sleepSeconds;
        private int returnValue;

        public ReturnAfterSleepCallable(int sleepSeconds, int returnValue) {
            this.sleepSeconds = sleepSeconds;
            this.returnValue = returnValue;
        }

        @Override
        public Integer call() throws Exception {
            System.out.println("begin to execute.");

            TimeUnit.SECONDS.sleep(sleepSeconds);

            System.out.println("end to execute.");

            return returnValue;
        }
    }


}

