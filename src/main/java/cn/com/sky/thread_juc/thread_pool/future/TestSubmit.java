package cn.com.sky.thread_juc.thread_pool.future;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestSubmit {

    public static void main(String[] args) {


//        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.CallerRunsPolicy());

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(20,
//                20,
//                0,
//                TimeUnit.SECONDS,
//                new ArrayBlockingQueue<>(100), new RejectedExecutionHandler() {
//            @Override
//            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
//                while (true) {
//                    if (executor.getQueue().size() < 100) {
//                        executor.submit(r);
//                        System.out.println(r);
//                        System.out.println(getPoolInfo(executor));
//                        break;
//                    } else if (executor.getQueue().size() == 100) {
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        System.out.println("rejectedExecution，threadId=" + Thread.currentThread().getId());
//                    }
//                }
//            }
//        });


        ThreadPoolExecutor executor = new ThreadPoolExecutor(20,
                20,
                0,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));

        for (int i = 0; i < 10000; i++) {
            executor.submit(new MyTaskSlow(i));
            executor.submit(new MyTaskMid(i));
            executor.submit(new MyTaskHigh(i));
//            System.out.println("getQueue" + executor.getQueue());
            System.out.println("main threadId=" + Thread.currentThread().getId());

        }

        System.out.println("finished..... ");

    }


    private String getPoolInfo(ThreadPoolExecutor executor) {
        Integer qsize = executor.getQueue().size();
        Integer activeCount = executor.getActiveCount();
        Long completedTaskCount = executor.getCompletedTaskCount();
        Long taskNoDone = executor.getTaskCount();
        StringBuffer sb = new StringBuffer();
        sb.append("【");
        sb.append(" 队列size=").append(qsize);
        sb.append(" 活动线程数=").append(activeCount);
        sb.append(" 已完成任务=").append(completedTaskCount);
        sb.append(" 未完成任务=").append(taskNoDone);
        sb.append("】");
        return sb.toString();
    }

    private static class MyTaskSlow implements Callable {

        private int i;

        MyTaskSlow(int i) {
            this.i = i;
        }

        @Override
        public Object call() throws Exception {
            System.out.println("slow slow slow " + i + " id=" + Thread.currentThread().getId());
            Thread.sleep(100);
            return null;
        }

        @Override
        public String toString() {
            return "MyTaskSlow" + i;
        }
    }

    private static class MyTaskMid implements Callable {
        private int i;

        MyTaskMid(int i) {
            this.i = i;
        }

        @Override
        public Object call() throws Exception {
            System.out.println("middle middle middle " + i + " id=" + Thread.currentThread().getId());
            Thread.sleep(200);
            return null;
        }

        @Override
        public String toString() {
            return "MyTaskMid" + i;
        }
    }

    private static class MyTaskHigh implements Callable {

        private int i;

        MyTaskHigh(int i) {
            this.i = i;
        }

        @Override
        public Object call() throws Exception {
            System.out.println("high high high " + i + " id=" + Thread.currentThread().getId());
            Thread.sleep(500);
            return null;
        }

        @Override
        public String toString() {
            return "MyTaskHigh" + i;
        }
    }

}