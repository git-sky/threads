package cn.com.sky.thread_juc.thread_pool.completionservice;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
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
 * 一般情况下，我们使用Runnable作为基本的任务表示形式，但是Runnable是一种有很大局限的抽象，run方法中只能记录日志，打印，
 * 或者把数据汇总入某个容器（一方面内存消耗大， 另一方面需要控制同步 ，效率很大的限制），总之不能返回执行的结果；
 * 比如同时1000个任务去网络上抓取数据，然后将抓取到的数据进行处理（处理方式不定），我觉得最好的方式就是提供回调接口，把处理的方式最为回调传进去 ；
 * 但是现在我们有了更好的方式实现：CompletionService + Callable
 *
 * Callable的call方法可以返回执行的结果;
 *
 * CompletionService将Executor（线程池）和BlockingQueue（阻塞队列）结合在一起，同时使用Callable作为任务的基本单元，
 * 整个过程就是生产者不断把Callable任务放入阻塞队列，Executor作为消费者不断把任务取出来执行，并返回结果；
 *
 * 优势：
 *
 * a、阻塞队列防止了内存中排队等待的任务过多，造成内存溢出（毕竟一般生产者速度比较快，比如爬虫准备好网址和规则，就去执行了，执行起来（消费者）还是比较慢的）
 *
 * b、CompletionService可以实现，哪个任务先执行完成就返回，而不是按顺序返回，这样可以极大的提升效率；
 *
 * 1、CompletionService ： Executor + BlockingQueue
 */
public class TestCompletionServiceDemo2 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /**
         * 内部维护11个线程的线程池
         */
        ExecutorService executorService = Executors.newFixedThreadPool(11);
        /**
         * 容量为10的阻塞队列
         */
        // final BlockingQueue<Future<Integer>> queue = new
        // LinkedBlockingDeque<Future<Integer>>(10);
        // final BlockingQueue<Future<Integer>> queue = new
        // LinkedBlockingQueue<Future<Integer>>(10);
        final BlockingQueue<Future<Integer>> queue = new ArrayBlockingQueue<>(10);

        // 实例化CompletionService
        final CompletionService<Integer> completionService = new ExecutorCompletionService<>(executorService, queue);

        // final CompletionService<Integer> completionService = new
        // ExecutorCompletionService<Integer>(exec);

        /**
         * 模拟瞬间产生10个任务，且每个任务执行时间不一致
         */
        for (int i = 0; i < 10; i++) {
            completionService.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int ran = new Random().nextInt(1000);
                    Thread.sleep(ran);
                    System.out.println(Thread.currentThread().getName() + " 休息时间：" + ran);
                    return ran;
                }
            });
        }

        /**
         * 立即输出结果
         */
        for (int i = 0; i < 10; i++) {
            try {
                // 谁最先执行完成，直接返回
                Future<Integer> f = completionService.take();
                System.out.println("执行时间：" + f.get());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

    }

}
