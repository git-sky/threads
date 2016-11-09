package cn.com.sky.threads.concurrent.timer;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 * 
 * 1、Timer管理延时任务的缺陷
 * 
 * a、以前在项目中也经常使用定时器，比如每隔一段时间清理项目中的一些垃圾文件，每个一段时间进行数据清洗；
 * 然而Timer是存在一些缺陷的，因为Timer在执行定时任务时只会创建一个线程， 所以如果存在多个任务，且任务时间过长，超过了两个任务的间隔时间，会发生一些缺陷 。
 * 
 * 下面定义了两个任务，预计是第一个任务1s后执行，第二个任务2s后执行，但是看运行结果： task2实际上是4s后才执行，正因为Timer内部是一个线程，而任务1所需的时间超过了两个任务间的间隔导致。
 * 
 * 使用ScheduledThreadPool解决这个问题,因为ScheduledThreadPool内部是个线程池，所以可以支持多个任务并发执行。
 * 
 * </pre>
 *
 */
public class TestTimerDelayBug {
	private static long start;

	public static void main(String[] args) throws Exception {

		TimerTask task1 = new TimerTask() {
			@Override
			public void run() {

				System.out.println("task1 invoked ! " + (System.currentTimeMillis() - start));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		};
		TimerTask task2 = new TimerTask() {
			@Override
			public void run() {
				System.out.println("task2 invoked ! " + (System.currentTimeMillis() - start));
			}
		};

		start = System.currentTimeMillis();

		// 1s后执行，4s后执行
		Timer timer = new Timer();
		timer.schedule(task1, 1000);
		timer.schedule(task2, 2000);

		// 1s后执行，2s后执行
		ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(2);
		newScheduledThreadPool.schedule(task1, 1000, TimeUnit.MILLISECONDS);
		newScheduledThreadPool.schedule(task2, 2000, TimeUnit.MILLISECONDS);

	}
}
