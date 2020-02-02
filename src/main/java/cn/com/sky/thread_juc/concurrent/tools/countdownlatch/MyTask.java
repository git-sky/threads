package cn.com.sky.thread_juc.concurrent.tools.countdownlatch;


import java.util.concurrent.CountDownLatch;

public class MyTask implements Runnable {
    private CountDownLatch downLatch;
    private int context;

    public MyTask(CountDownLatch downLatch, int context) {
        this.downLatch = downLatch;
        this.context = context;
    }

    @Override
    public void run() {
        doTask();
        downLatch.countDown();
    }

    private void doTask() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("do Task");
    }
}