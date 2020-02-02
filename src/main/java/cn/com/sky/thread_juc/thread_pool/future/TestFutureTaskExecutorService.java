package cn.com.sky.thread_juc.thread_pool.future;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * <pre>
 *
 * submit(Runnable task)--->new FutureTask(Runnable task,T value)--->new Sync(Executors.callable(runnable, result))-->
 * new RunnableAdapter<T>(task, result)-->task.run();
 *
 * submit(Callable task)--->new FutureTask<T>(callable)---> new Sync(callable)
 *
 * execute(Runnable command)
 *
 *
 * </pre>
 */
public class TestFutureTaskExecutorService {

    @Test
    public void testExecutorService() {
        // 第一种方式
//         ExecutorService executor = Executors.newCachedThreadPool();
//
        ThreadPoolExecutor executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());


        //futureTask与future是不同的对象，返回的值不一样。
        FutureTask<Integer> futureTask = new FutureTask<>(new TaskCallable());
        Future future = executor.submit(futureTask);
        executor.execute(futureTask);
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("abc");
//            }
//        });
        executor.shutdown();

        System.out.println(future);
        System.out.println(futureTask);

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }

        System.out.println("主线程在执行任务");

        try {
            System.out.println("task运行结果" + futureTask.get());
            System.out.println("task运行结果" + future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");

    }

    @Test
    public void testThread() {
        // 第二种方式，注意这种方式和第一种方式效果是类似的，只不过一个使用的是ExecutorService，一个使用的是Thread
        FutureTask<Integer> futureTask = new FutureTask<>(new TaskCallable());
        Thread thread = new Thread(futureTask);
        thread.start();

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e1) {
//            e1.printStackTrace();
//        }

        System.out.println("主线程在执行任务");

        try {
            System.out.println("task运行结果" + futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        System.out.println("所有任务执行完毕");

    }

    private class TaskCallable implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(2000);
            int sum = 0;
            for (int i = 0; i < 100; i++) {
                sum += i;
            }
            return sum;
        }
    }

}
