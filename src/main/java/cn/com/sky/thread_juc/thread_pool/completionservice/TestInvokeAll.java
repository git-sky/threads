package cn.com.sky.thread_juc.thread_pool.completionservice;

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
 * 2、ExecutorService.invokeAll
 *
 * ExecutorService 的 invokeAll 方法也能批量执行任务，并批量返回结果，
 * 但是必须等待所有的任务执行完成后统一返回，一方面内存持有的时间长；
 * 另一方面响应性也有一定的影响，毕竟大家都喜欢看看刷刷的执行结果输出，而不是苦苦的等待；
 *
 * </pre>
 */
public class TestInvokeAll {

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<Integer>> tasks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Callable<Integer> task = new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int ran = new Random().nextInt(1000);
                    System.out.println(Thread.currentThread() + ",ran:" + ran);
                    Thread.sleep(ran);
                    System.out.println(Thread.currentThread().getName() + " sleep: " + ran);
                    return ran;
                }
            };

            tasks.add(task);
        }

        for (Callable<Integer> callable : tasks) {
            System.out.println(callable);
        }

        long s = System.currentTimeMillis();

        //等待所有的任务执行完成后,统一返回。
        List<Future<Integer>> results = executorService.invokeAll(tasks);

        System.out.println("等待所有的任务执行完成后,统一返回。invokeAll: " + (System.currentTimeMillis() - s) + " ms");

        for (int i = 0; i < results.size(); i++) {
            try {
                System.out.println(results.get(i).get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

    }

}
