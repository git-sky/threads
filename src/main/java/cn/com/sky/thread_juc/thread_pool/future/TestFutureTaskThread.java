package cn.com.sky.thread_juc.thread_pool.future;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * <pre>
 *
 * RunnableFuture 继承了Runnable接口和 Future接口，而FutureTask实现了 RunnableFuture接口。
 * 所以它既可以作为Runnable被线程执行， 又可以作为Future得到Callable的返回值。
 *
 * FutureTask(Callable callable)
 *
 * </pre>
 */
public class TestFutureTaskThread {

    @Test
    public void test() {

        FutureTask<Integer> ftask = new FutureTask<>(new WorkerCallable());
        new Thread(ftask).start();


        //主线程等待子线程执行完成
        while (!ftask.isDone()) {
            try {
                System.out.println("not ok...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            int amount = ftask.get();
            System.out.println("amount=" + amount);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class WorkerCallable implements Callable<Integer> {
        private int hours = 12;
        private int amount;

        @Override
        public Integer call() throws Exception {
            while (hours > 0) {
                System.out.println("I'm working......");
                amount++;
                hours--;
                Thread.sleep(1000);
            }
            return amount;
        }
    }
}