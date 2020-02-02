package cn.com.sky.thread_juc.thread_pool.completionservice;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * <pre>
 *
 * CompletionService 常用于运行后合并结果。
 *
 * 此类将安排那些完成时提交的任务，把它们结果放置在可使用 take 访问的队列上。该类非常轻便，适合于在执行几组任务时临时使用。
 *
 * 举一个小例子来说明下：
 *
 * 假设不使用等差队列求和公式来计算从1加到500000000的和，分两种方式计算。例子如下
 */
public class TestCompletionServiceDemo {
    private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 5);

    public static void main(String[] args) {
        UseCommonCal();
        UseComletionService();
    }

    public static void UseCommonCal() {
        long beginTime = System.currentTimeMillis();
        long sum = 0L;
        for (int i = 1; i <= 500000000; i++) {
            sum += i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("UseCommonCal,从1加到500000000的和是:" + sum);
        System.out.println("UseCommonCal,Use time:" + (endTime - beginTime));
    }

    public static void UseComletionService() {
        CompletionService<Long> completionService = new ExecutorCompletionService<>(pool);
        long beginTime = System.currentTimeMillis();
        int stmp = 0;
        int etmp = 5000000;
        int k = 5000000;
        for (int i = 0; i < 100; i++) {
            final int begin = stmp + 1;
            final int end = etmp;
            completionService.submit(new Callable<Long>() {
                public Long call() throws Exception {
                    long result = 0;
                    for (int j = begin; j <= end; j++) {
                        result += j;
                    }
                    return result;
                }
            });
            stmp = etmp;
            etmp = etmp + k;
        }
        long sum = 0L;
        try {

            for (int i = 0; i < 100; i++) {
                Future<Long> future = completionService.take();
                sum += future.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("UseComletionService,从1加到500000000的和是:" + sum);
        System.out.println("UseComletionService,Use time:" + (endTime - beginTime));
        pool.shutdown();
    }


}