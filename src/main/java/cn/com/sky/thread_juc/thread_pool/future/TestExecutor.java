package cn.com.sky.thread_juc.thread_pool.future;


import java.util.concurrent.Executor;

public class TestExecutor {

    public static void main(String[] args) {

        //Executor的简单实现
        Executor executor = new Executor() {
            @Override
            public void execute(Runnable r) {
                new Thread(r).start();
            }
        };

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("running....");
            }
        });
    }
}