package cn.com.sky.thread_juc.thread_pool.future;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class TestSubmitSeq {

    @Test
    public void test() {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                while (true) {
                    if (executor.getPoolSize() < 10) {
                        executor.submit(r);
                        break;
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        List<FutureTask> list = new ArrayList();
        for (int i = 0; i < 100; i++) {
            FutureTask futureTask = new FutureTask(new Task(i, executor));
            executor.submit(futureTask);
            list.add(futureTask);
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (FutureTask futureTask : list) {
            try {
                System.out.println(futureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    private String getPoolInfo(ThreadPoolExecutor executor) {
        StringBuilder sb = new StringBuilder();
        sb.append(",getPoolSize=" + executor.getPoolSize());
        sb.append(",getCorePoolSize=" + executor.getCorePoolSize());
        sb.append(",getMaximumPoolSize=" + executor.getMaximumPoolSize());
        sb.append(",getActiveCount=" + executor.getActiveCount());
        sb.append(",getCompletedTaskCount=" + executor.getCompletedTaskCount());
        sb.append(",getTaskCount=" + executor.getTaskCount());
        sb.append(",getRejectedExecutionHandler=" + executor.getRejectedExecutionHandler());
//        sb.append(",getQueue=" + executor.getQueue());

        return sb.toString();
    }


    private class Task implements Callable {
        private Integer id;
        private ThreadPoolExecutor executor;

        public Task(Integer id) {
            this.id = id;
        }

        public Task(Integer id, ThreadPoolExecutor executor) {
            this.id = id;
            this.executor = executor;
        }

        @Override
        public Object call() throws Exception {
            Integer sleep = new Random().nextInt(5) * 1000;
            Thread.sleep(sleep);
            System.out.println("id=" + id);
            System.out.println(getPoolInfo(executor));
            return id;
        }
    }

}
