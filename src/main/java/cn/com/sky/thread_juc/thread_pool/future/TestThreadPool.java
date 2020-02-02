package cn.com.sky.thread_juc.thread_pool.future;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <pre>
 *
 * Java线程：线程池
 *
 * ExecutorService中，execute与submit区别:
 *
 * 1.execute没有返回值，submit有返回值Future。
 * 2.execute有一种参数 Runnable，submit有两种参数 Runnable 与 Callable。
 * 3.可以根据 submit 的返回值 Future 处理很多事情，捕获异常，取消任务等等。
 * 4.Future submit(Runnable task)，future.get()返回null。
 *
 * 方法定义如下：
 *  1. execute(Runnable command);
 *
 *  2. Future submit(Runnable task);
 *    执行流程：
 *    a> RunnableFuture<T> ftask = newTaskFor(task, null);
 *
 *    newTaskFor(Runnable runnable, T value)--->new FutureTask(Runnable task,T value)--->new Sync(Executors.callable(runnable, result))--> new RunnableAdapter<T>(task, result)-->call()-->task.run();
 *
 *    b> execute(Runnable command)
 *
 *  3. Future submit(Runnable task, T result);
 *    执行流程：
 *    a> RunnableFuture<T> ftask = newTaskFor(task, result);
 *
 *    newTaskFor(Runnable runnable, T value)--->new FutureTask(Runnable task,T value)--->new Sync(Executors.callable(runnable, result))--> new RunnableAdapter<T>(task, result)-->call()-->task.run();
 *
 *    b> execute(Runnable command)
 *
 *
 *  4. Future submit(Callable task);
 *    执行流程：
 *    a> RunnableFuture<T> ftask = newTaskFor(task);
 *
 *    newTaskFor(Callable<T> callable)---> new FutureTask<T>(callable)---> new Sync(callable);
 *
 *    b> execute(Runnable command)
 *
 *
 *  (2/3/4)> FutureTask.run()--->  sync.innerRun()---> callable.call()---> sync.innerSet(v)---> done()(ExecutorCompletionService类实现了这个方法);
 *
 * 本质上最后都是调用的 execute(Runnable command) 方法。
 *
 *  Callable 执行本质上还是被Thread类的run方法调用执行。
 *
 *
 * shutdown()
 * 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。
 *
 *
 * shutdownNow()
 * 阻止等待任务的启动并试图停止当前正在执行的任务。
 */
public class TestThreadPool {


    @Test
    public void test() {
        // 创建一个可重用固定线程数的线程池
//         ExecutorService pool = Executors.newFixedThreadPool(2);
        //
        // //创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
        // ExecutorService pool = Executors.newSingleThreadExecutor();
        //
        // 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
        // ExecutorService pool = Executors.newCachedThreadPool();


        // 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
        Thread t1 = new MyThread("t1");
        Thread t2 = new MyThread("t2");
        Thread t3 = new MyThread("t3");
        Thread t4 = new MyThread("t4");
        Thread t5 = new MyThread("t5");

        ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        // 将线程放入池中进行执行
        pool.execute(t1);
        pool.execute(t2);
        pool.execute(t3);
        pool.execute(t4);
        pool.execute(t5);

        pool.submit(t1);
        pool.submit(t1, "result");
        Future future = pool.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                Thread.sleep(2000);
                return "call";
            }
        });

        // Executors.callable(task)//runnable转callable

        System.out.println("after submit..........");

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 关闭线程池
        pool.shutdown();
    }

    class MyThread extends Thread {
        private List<Object> list = new ArrayList<>();

        MyThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                list.add(new Object());
                System.out.println(Thread.currentThread().getName() + " is running。。。");
            }
            System.out.println(Thread.currentThread().getName() + " finished。。。");
        }
    }

}

