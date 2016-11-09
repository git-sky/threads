package cn.com.sky.threads.concurrent.cyclicbarrier;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCyclicBarrier2 {

	public static void main(String[] args) {
		
		ExecutorService service = Executors.newCachedThreadPool();
		
		//到达公共屏障点的时候都最先执行这个传进去的Runnable，然后再执行处于等待的Runnable。
		final CyclicBarrier cb = new CyclicBarrier(3, new Runnable() {
			@Override
			public void run() {
				System.out.println("********我最先执行***********");
			}
		});
		
		for (int i = 0; i < 3; i++) {
			Runnable runnable = new Runnable() {
				public void run() {
					try {
						
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点1，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
						cb.await();// 到此如果没有达到公共屏障点，则该线程处于等待状态，如果达到公共屏障点则所有处于等待的线程都继续往下运行

						Thread.sleep((long) (Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点2，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
						cb.await(); // 这里CyclicBarrier对象又可以重用
						
						Thread.sleep((long) (Math.random() * 10000));
						System.out.println("线程" + Thread.currentThread().getName() + "即将到达集合地点3，当前已有" + cb.getNumberWaiting() + "个已经到达，正在等候");
						cb.await();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			service.execute(runnable);
		}
		service.shutdown();
	}
}