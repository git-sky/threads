package cn.com.sky.thread_juc.thread_pool.future;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <pre>
 *
 * Future f = submit(Runnable task)
 * f.get返回null
 *
 * 当将一个Callable的对象传递给ExecutorService的submit方法，则该call方法自动在一个线程上执行，并且会返回执行结果Future对象。
 * 同样，将Runnable的对象传递给ExecutorService的submit方法，则该run 方法自动在一个线程上执行，并且会返回执行结果Future对象，但是在该Future对象上调用get方法，将返回null。
 *
 * </pre>
 */
public class TestCallable {

    @Test
    public void test() {
        ExecutorService pool = Executors.newCachedThreadPool();

        // Future相当于是用来存放Executor执行的结果的一种容器
        List<Future<?>> results = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            results.add(pool.submit(new TaskCallable(i)));
//            results.add(pool.submit(new TaskRunnable(i), "run id result=" + i));
        }

        System.out.println("after submit....");

        try {
            for (Future fs : results) {
                if (fs.isDone()) {
                    System.out.println("completed: " + fs.get());
                } else {
                    System.out.println("Future result is not yet complete: " + fs.get());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pool.shutdown();
    }

    private class TaskCallable implements Callable<Object> {
        private int id;

        public TaskCallable(int id) {
            this.id = id;
        }

        @Override
        public Object call() throws Exception {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "call result=" + id;
        }
    }

    private class TaskRunnable implements Runnable {
        private int id;

        public TaskRunnable(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("running, id=" + id);
        }
    }

}
