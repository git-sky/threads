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

    private static ThreadLocal<Integer> threadLocal = new InheritableThreadLocal<>();


    @Test
    public void test() {
        ExecutorService pool = Executors.newCachedThreadPool();

        // Future相当于是用来存放Executor执行的结果的一种容器
        List<Future<?>> results = new ArrayList<>();

        threadLocal.set(11);

        System.out.println("call parent 1=" + threadLocal.get());


        try {
            for (int i = 0; i < 10; i++) {
                results.add(pool.submit(new TaskCallable(i)));
//            results.add(pool.submit(new TaskRunnable(i), "run id result=" + i));
            }
        } catch (Exception e) {
            System.out.println("Exception====");
        }

        System.out.println("after submit.................");

        threadLocal.set(12);
        System.out.println("call parent 2=" + threadLocal.get());


        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            for (Future fs : results) {
                if (fs.isDone()) {
                    System.out.println("completed: " + fs.get());
                } else {
                    System.out.println("Future result is not yet complete: " + fs.get());
                }
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException...........");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("ExecutionException...........");
            e.printStackTrace();
        }

        System.out.println(" pool.shutdown..............");
        pool.shutdown();
    }

    private class TaskCallable implements Callable<Object> {
        private int id;

        public TaskCallable(int id) {
            this.id = id;
        }

        @Override
        public Object call() throws Exception {
//            try {
//                Thread.sleep(3000);
            System.out.println("call....id=" + id);

            System.out.println("call 1=" + threadLocal.get());

            threadLocal.set(21);

            System.out.println("call 2=" + threadLocal.get());

            throw new RuntimeException("call exception");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return "call result=" + id;
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
