package cn.com.sky.thread_juc.thread_pool.completionservice;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCompletionService4 implements Callable<String> {

    private int id;

    public TestCompletionService4(int i) {
        this.id = i;
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);
        for (int i = 0; i < 10; i++) {
            completionService.submit(new TestCompletionService4(i));
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(completionService.take().get());
        }
        executorService.shutdown();
    }

    public String call() throws Exception {
        Integer time = (int) (Math.random() * 1000);
        try {
            System.out.println(this.id + " start");
            Thread.sleep(time);
            System.out.println(this.id + " end");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.id + ":" + time;
    }
}