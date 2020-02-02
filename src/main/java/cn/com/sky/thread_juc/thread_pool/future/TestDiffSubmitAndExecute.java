package cn.com.sky.thread_juc.thread_pool.future;

import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.*;


/**
 * <pre>
 *
 *  ExecutorService中，execute与submit的4点区别:
 *
 *  1.execute没有返回值，submit有返回值Future。
 *
 *  2.execute有一种参数Runnable，submit有两种参数Runnable与Callable。
 *
 *  3.可以根据submit的返回值Future处理很多事情，捕获异常，取消任务等等。
 *
 *  4.Future submit(Runnable task)，future.get()返回null。
 *
 *
 *   void execute(Runnable command);
 *   Future submit(Runnable task);
 *   Future submit(Callable task);
 *
 *
 * 注意：execute(Runnable x) 与submit(Runnable x) 的区别：
 *
 * 1、execute(Runnable x) 没有返回值。可以执行任务，但无法判断任务是否成功完成；无法获取异常信息；
 * submit(Runnable x) 返回一个future。可以执行任务，并可以用这个返回的 future 来判断任务是否成功完成；可以获取异常信息；
 *
 * 2、execute(Runnable x) 任务在其执行过程中一旦抛出了未捕获的异常，则对其进行执行的工作者线程就会异常终止。
 *    submit(Runnable x) 任务在其执行过程中即便是抛出了未捕获的异常，也不会导致对其进行执行的工作者线程异常终止。
 *
 * 3、通过ThreadPoolExecutor.submit 调用提交给线程池执行的任务，
 * 其执行过程中抛出的未捕获异常并不会导致与该线程池中的工作者结程关联的 UncaughtExceptionHandler 的 uncaughtException 方法被调用 。
 *
 * </pre>
 */
public class TestDiffSubmitAndExecute {

    @Test
    public void testThread() {
        try {
            //1、无法判断任务是否成功执行完成；
            //2、无法获取异常信息；
            new Thread(new MyRunableTask("fail")).start();
        } catch (Throwable e) {
            //捕获不到异常
            System.out.println("+++++++++++++++++");
        }
        System.out.println("============testThread============");
    }


    @Test
    public void testExecute() {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        /**
         * execute(Runnable x) 没有返回值。可以执行任务，但无法判断任务是否成功完成。也无法捕获异常。
         */
        try {
            //1、无法判断任务是否成功执行完成；
            //2、无法获取异常信息；
            for (int i = 0; i < 10; i++) {
                //如果任务是通过 ThreadPoolExecutor.execute方法提交给线程池的，那么这些任务在其执行过程中一旦抛出了未捕获的异常，
                //则对其进行执行的工作者线程就会异常终止。 尽管 ThreadPoolExecutor 能够侦测到这种情况并在工作者线程异常终止的时候创建井启动新的
                // 替代工作者线程，但是由于线程的创建与启动都有其开销，因此这种情形下我们会尽量避免任务在其执行过程中抛出未捕获的异常 。
                pool.execute(new MyRunableTask("fail"));
            }
        } catch (Throwable e) {
            //捕获不到异常
            System.out.println("=====================");
        }

        pool.shutdown();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testSubmit() {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        /*
         * submit(Runnable x) 返回一个future。可以用这个future来判断任务是否成功完成。请看下面：
         */
        //1、可以判断任务是否成功执行完成；
        //2、可以获取异常信息；
        for (int i = 0; i < 10; i++) {
            // 如果任务是通过 ThreadPoolExecutor.submit调用提交给线程池的，
            // 那么这些任务在其执行过程中即便是抛出了未捕获的异常也不会导致对其进行执行的工作者线程异常终止。
            // 这种情形下任务所抛出的异常可以通过 Future.get()所抛出的 ExecutionException来获取。
            Future future = pool.submit(new MyRunableTask("fail"));
            try {
                if (future.get() == null) {// 如果Future's get返回null，任务完成
                    System.out.println("任务完成");
                }
            } catch (InterruptedException e) {
                System.out.println("occur InterruptedException ....");
            } catch (ExecutionException e) {
                // 可以查看任务失败的原因是什么
                System.out.println("occur ExecutionException! error message: " + e.getCause().getMessage());
            }
        }

        System.out.println("=============");

    }


    @Test
    public void testSubmitInputFuture() {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 10; i++) {
            FutureTask futureTask = new FutureTask<>(new MyRunableTask("fail"), "abc");
            Future outFuture = pool.submit(futureTask, "xyz");
            try {
                //futureTask与outFuture是不同的对象。
                System.out.println("futureTask=" + futureTask);
                System.out.println("outFuture=" + outFuture);
                System.out.println("futureTask.get()=" + futureTask.get());
                System.out.println("outFuture.get()=" + outFuture.get());
            } catch (InterruptedException e) {
                System.out.println("occur InterruptedException ....");
            } catch (ExecutionException e) {
                System.out.println("occur ExecutionException! error message: " + e.getCause().getMessage());
            }
        }

        System.out.println("testSubmitInputFuture=============");
    }


    private class MyRunableTask implements Runnable {
        private String taskName;

        public MyRunableTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            System.out.println("id:" + Thread.currentThread().getId() + ", taskName: " + taskName);
            if (Objects.equals(taskName, "fail")) {
                throw new RuntimeException("RuntimeException from inside " + taskName);
            }
            System.out.println("finish.....");
        }
    }

}


