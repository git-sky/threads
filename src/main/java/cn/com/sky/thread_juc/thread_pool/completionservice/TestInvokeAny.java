package cn.com.sky.thread_juc.thread_pool.completionservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *
 * 有一类多线程编程模式是这样的：启动多个线程，相互独立的（无同步）去计算一个结果，当某一个线程得到结果之后，立刻终止所有线程，因为只需要一个结果就够了。
 *
 * ExecutorService.invokeAny()就是为此设计的，他接收的参数是一个List，List中的每一个元素必须实现Callable接口。
 * 他的功能是依此启动新的线程执行List中的任务，并将第一个得到的结果作为返回值，然后立刻终结所有的线程。其用法如下：
 *
 * String s =  es.invokeAny(list)
 * invokeAny()和任务的提交顺序无关，只是返回最早正常执行完成的任务。
 *
 * 基于以下类实现：
 * ExecutorCompletionService +  LinkedBlockingQueue + QueueingFuture（实现了done()方法）
 *
 * </pre>
 */
public class TestInvokeAny {

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

        long s = System.currentTimeMillis();

        Integer result = executorService.invokeAny(tasks);

        System.out.println("invokeAny: " + (System.currentTimeMillis() - s) + " ms");
        System.out.println("result=" + result);

        executorService.shutdown();

    }

}
