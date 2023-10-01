package cn.com.sky.thread_juc.thread_pool.scheduledThreadPoolExecutor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * 鉴于 Timer 的缺陷，Java 5 推出了基于线程池设计的 ScheduledExecutor。
 *
 * 其设计思想是，每一个被调度的任务都会由线程池中一个线程去执行 ，因此任务是并发执行的，相互之间不会受到干扰。需要注意的是，只有当任务的执行时间到来时，ScheduedExecutor
 * 才会真正启动一个线程，其余时间 ScheduledExecutor 都是在轮询任务的状态。
 */
public class TestScheduledExecutor implements Runnable {
    private String jobName = "";

    public TestScheduledExecutor(String jobName) {
        super();
        this.jobName = jobName;
    }

    @Override
    public void run() {
        System.out.println("execute " + jobName);
    }

    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(10);

        Executors.newSingleThreadScheduledExecutor();

        //向定时任务线程池提交一个延时Runnable任务（仅执行一次）
        service.schedule(new TestScheduledExecutor("job1"), 30, TimeUnit.SECONDS);


        long initialDelay1 = 1;
        long period1 = 1;

        //向定时任务线程池提交一个固定时间间隔执行的任务
        // 从现在开始1秒钟之后，每隔1秒钟执行一次job1
        service.scheduleAtFixedRate(new TestScheduledExecutor("job1"), initialDelay1, period1, TimeUnit.SECONDS);

        long initialDelay2 = 2;
        long period2 = 2;

        //向定时任务线程池提交一个固定延时间隔执行的任务
        // 从现在开始2秒钟之后，每隔2秒钟执行一次job2
        service.scheduleWithFixedDelay(new TestScheduledExecutor("job2"), initialDelay2, period2, TimeUnit.SECONDS);
    }
}