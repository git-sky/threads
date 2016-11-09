package cn.com.sky.threads.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatchOrder {

	public static void main(String[] args) {

		ExecutorService service = Executors.newCachedThreadPool();

		// 发布命令标志
		final CountDownLatch cdOrder = new CountDownLatch(1);
		// 执行完命令标志
		final CountDownLatch cdAnswer = new CountDownLatch(3);

		for (int i = 0; i < 3; i++) {
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						System.out.println("线程" + Thread.currentThread().getName() + "正准备接受命令");
						cdOrder.await();
						System.out.println("线程" + Thread.currentThread().getName() + "已接受命令,正在执行");
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() + "已执行完命令");
						cdAnswer.countDown();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			service.execute(runnable);
		}
		try {
			Thread.sleep((long) (Math.random() * 10000));
			System.out.println("线程" + Thread.currentThread().getName() + "即将发布命令");
			cdOrder.countDown();
			System.out.println("线程" + Thread.currentThread().getName() + "已发布命令，正在等待执行结果");
			cdAnswer.await();
			Thread.sleep((long) (Math.random() * 10000));
			System.out.println("线程" + Thread.currentThread().getName() + "已收到所有命令结果");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		service.shutdown();
	}
}