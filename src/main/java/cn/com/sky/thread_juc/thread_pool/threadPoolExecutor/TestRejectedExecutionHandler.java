package cn.com.sky.thread_juc.thread_pool.threadPoolExecutor;

import org.junit.Test;

import java.util.concurrent.*;


public class TestRejectedExecutionHandler {

    @Test
    public void test() throws InterruptedException {

        //不丢弃任务，调用者自己执行任务（主线程）；
        RejectedExecutionHandler policy = new ThreadPoolExecutor.CallerRunsPolicy();

        //抛出异常，默认策略
        policy = new ThreadPoolExecutor.AbortPolicy();

        //偷偷丢弃任务
        policy = new ThreadPoolExecutor.DiscardPolicy();

        //丢弃队列中最早的任务，并尝试执行新任务
        policy = new ThreadPoolExecutor.DiscardOldestPolicy();


        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), policy);


        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(new MyThread(i));
        }

        threadPoolExecutor.shutdown();

        Thread.sleep(10000);

        System.out.println("finish..........");

    }

    class MyThread extends Thread {
        private int index;

        public MyThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            try {
                System.out.println("[" + index + "] start....");
//            Thread.sleep((int) (Math.random() * 1000));

                Thread.sleep(1000);

                System.out.println("[" + index + "] end....");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}