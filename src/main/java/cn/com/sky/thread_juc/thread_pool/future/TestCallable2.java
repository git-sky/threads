package cn.com.sky.thread_juc.thread_pool.future;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * <pre>
 *
 * Java线程：有返回值的线程
 *
 * Callable 和 Runnable 的使用方法大同小异， 区别在于：
 *
 * 1.Callable 使用 call（） 方法， Runnable 使用 run() 方法
 *
 * 2.call() 可以返回值， 而 run()方法不能返回。
 *
 * 3.call() 可以抛出受检查的异常，比如ClassNotFoundException， 而run()不能抛出受检查的异常。
 *
 * 4.运行Callable任务可以拿到一个Future对象，表示异步计算的结果。它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。
 * 通过Future对象可以了解任务执行情况，可取消任务的执行，还可获取执行结果。
 *
 * </pre>
 */
public class TestCallable2 {

    @Test
    public void test() {

        // 创建一个线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);

        // 创建两个有返回值的任务
        Callable<?> c1 = new MyCallable("A");
        Callable<?> c2 = new MyCallable("B");

        // 执行任务并获取Future对象
        Future<?> f1 = pool.submit(c1);
        Future<?> f2 = pool.submit(c2);

        System.out.println("after submit..........................");

        // 从Future对象上获取任务的返回值，并输出到控制台
        try {
            System.out.println(">>> " + f1.get());
            System.out.println(">>> " + f2.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 关闭线程池
        pool.shutdown();
    }

    private class MyCallable implements Callable<String> {
        private String taskName;

        MyCallable(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public String call() throws Exception {
            for (int i = 0; i < 10; i++) {
                System.out.println(Thread.currentThread().getName() + " i=" + i);
            }
            return taskName + "任务返回的内容";
        }
    }
}

