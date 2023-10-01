package cn.com.sky.thread_juc.concurrent.tools.countdownlatch;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        CountDownLatch downLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            executorService.execute(new MyTask(downLatch, i));
        }

        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }
}