package cn.com.sky.thread_juc.thread_pool.future;

import java.util.concurrent.*;


/**
 * UncaughtExceptionHandler 只有在execute.execute()方法中才生效，在execute.submit中是无法捕获到异常的。
 */
public class ThreadPoolException {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService execute =Executors.newFixedThreadPool(1);
//    new ThreadPoolExecutor(1, 1,
//                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactoryBuilder().setUncaughtExceptionHandler(new MyHandler()).build());

        execute.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("=====11=======");
            }
        });

        TimeUnit.SECONDS.sleep(5);
        execute.execute(new Run1());
    }


    private static class Run1 implements Runnable {
        @Override
        public void run() {
            int count = 0;
            while (true) {
                count++;
                System.out.println("-------222-------------count=" + count);

                if (count == 10) {
                    System.out.println(1 / 0);
                    try {
                    } catch (Exception e) {
                        System.out.println("Exception....");
                    }
                }

                if (count == 20) {
                    System.out.println("count=" + count);
                    break;
                }
            }
        }
    }
}

class MyHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("threadId = " + t.getId() + ", threadName = " + t.getName() + ", ex =" + e.getMessage());
    }
}