package cn.com.sky.thread_juc.thread_pool.future;

import java.util.concurrent.*;

/**
 * 基于：Unsafe和LockSupport实现，Treiber stack结构。
 * <p>
 * awaitDone：构造Treiber stack结构，并使用LockSupport.park(this)，阻塞当前线程
 * finishCompletion：使用LockSupport.unpark(t)唤醒t线程，唤醒等待链条（Treiber stack结构）上的所有线程。
 * <p>
 * WaitNode类型的单链表。链表栈结构，lock-free的无界stack （Treiber算法）。
 */
public class TestFutureTaskSource {

    public static void main(String args[]) {

        FutureTask<Integer> ftask = new FutureTask<>(new WorkerCallable());
        new Thread(ftask).start();

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.submit(new WorkerRunable(ftask));
        executorService.submit(new WorkerRunable(ftask));
        executorService.submit(new WorkerRunable(ftask));
        executorService.submit(new WorkerRunable(ftask));
        executorService.submit(new WorkerRunable(ftask));
    }

    static class WorkerRunable implements Runnable {
        FutureTask<Integer> ftask;

        WorkerRunable(FutureTask<Integer> ftask) {
            this.ftask = ftask;
        }

        @Override
        public void run() {
            try {
                ftask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    static class WorkerCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            Thread.sleep(100000);
            return 1;
        }
    }
}