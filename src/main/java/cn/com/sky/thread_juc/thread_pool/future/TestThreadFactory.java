package cn.com.sky.thread_juc.thread_pool.future;


import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * 线程创建工厂
 */
public class TestThreadFactory {

    public static void main(String[] args) {
        SimpleThreadFactory simpleThreadFactory = new SimpleThreadFactory();
        Thread t = simpleThreadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("simpleThreadFactory new thread....");
            }
        });
        t.start();


        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        Thread t2 = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                System.out.println("defaultThreadFactory running...");
            }
        });
        t2.start();

    }
}


class SimpleThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r);
    }
}