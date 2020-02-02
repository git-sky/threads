package cn.com.sky.thread_juc.thread_pool.future;


import java.util.concurrent.Executors;

public class TestExecutors {

    public static void main(String[] args) {

        Executors.newSingleThreadExecutor();
        Executors.newFixedThreadPool(1);
        Executors.newCachedThreadPool();

    }

}