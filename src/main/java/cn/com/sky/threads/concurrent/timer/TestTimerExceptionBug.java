package cn.com.sky.threads.concurrent.timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 
 * 2、Timer当任务抛出异常时的缺陷
 * 
 * 如果TimerTask抛出RuntimeException，Timer会停止所有任务的运行.
 * 但是ScheduledExecutorService可以保证，task1出现异常时，不影响task2的运行 。
 * 
 * 3、Timer执行周期任务时依赖系统时间
 * 
 * Timer执行周期任务时依赖系统时间，如果当前系统时间发生变化会出现一些执行上的变化，ScheduledExecutorService基于时间的延迟，不会由于系统时间的改变发生执行变化。
 *
 */
public class TestTimerExceptionBug {

	public static void main(String[] args) throws InterruptedException {

		final TimerTask task1 = new TimerTask() {

			@Override
			public void run() {
				throw new RuntimeException();
			}
		};

		final TimerTask task2 = new TimerTask() {

			@Override
			public void run() {
				System.out.println("task2 invoked!");
			}
		};

		// 由于任务1抛出异常，任务2也停止运行了
		Timer timer = new Timer();
		timer.schedule(task1, 100);
		timer.scheduleAtFixedRate(task2, new Date(), 1000);

		// task2继续运行不受影响
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
		pool.schedule(task1, 100, TimeUnit.MILLISECONDS);
		pool.scheduleAtFixedRate(task2, 0, 1000, TimeUnit.MILLISECONDS);

	}
}
