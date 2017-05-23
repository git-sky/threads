package cn.com.sky.threads.concurrent.cyclicbarrier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 
 * CyclicBarrier:栅栏，加法器
 * 
 * 所有线程必须同时到达栅栏位置，才能继续执行。
 * 栅栏则是所有线程相互等待，直到所有线程都到达某一点时才打开栅栏，然后线程可以继续执行。
 * 
 * 
 * 栅栏（Barrier）类似于闭锁，它能阻塞一组线程直到某个事件发生。栅栏与闭锁的关键区别在于，所有线程必须同时到达栅栏位置，才能继续执行。
 * 闭锁用于等待事件，而栅栏用于等待其他线程。（栅栏则是所有线程相互等待，直到所有线程都到达某一点时才打开栅栏，然后线程可以继续执行。）
 * 　　CyclicBarrier 可以使一定数量的参与方反复地在栅栏位置汇集，它在并行迭代算法中非常有用。CyclicBarrier支持一个可选的 Runnable 参数，当线程通过栅栏时，runnable对象将被调用。
 * 构造函数CyclicBarrier(int parties, Runnable barrierAction)，当线程在CyclicBarrier对象上调用await()方法时，栅栏的计数器将增加1，当计数器为parties时，栅栏将打开。
 */
public class TestCyclicBarrier {
	// 徒步需要的时间: Shenzhen, Guangzhou, Shaoguan, Changsha, Wuhan
	private static int[] timeWalk = { 3, 5, 8, 8, 6 };
	// 自驾游
	private static int[] timeSelf = { 1, 2, 3, 3, 4 };
	// 旅游大巴
	private static int[] timeBus = { 1, 2, 3, 3, 6 };

	static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(new Date()) + ": ";
	}

	static class Tour implements Runnable {
		private CyclicBarrier barrier;
		private String tourName;
		private int[] times;

		public Tour(CyclicBarrier barrier, String tourName, int[] times) {
			this.barrier = barrier;
			this.tourName = tourName;
			this.times = times;
		}

		public void run() {
			try {

				Thread.sleep(times[0] * 1000);
				System.out.println(now() + tourName + " Reached Shenzhen");
				barrier.await();
				System.out.println(now() + " All tour has reached Shenzhen. Go to the next city ---> Guangzhou");

				Thread.sleep(times[1] * 1000);
				System.out.println(now() + tourName + " Reached Guangzhou");
				barrier.await();
				System.out.println(now() + " All tour has reached Guangzhou. Go to the next city ---> Shaoguan");

				Thread.sleep(times[2] * 1000);
				System.out.println(now() + tourName + " Reached Shaoguan");
				barrier.await();
				System.out.println(now() + " All tour has reached Shaoguan. Go to the next city ---> Changsha");

				Thread.sleep(times[3] * 1000);
				System.out.println(now() + tourName + " Reached Changsha");
				barrier.await();
				System.out.println(now() + " All tour has reached Changsha. Go to the next city ---> Wuhan");

				Thread.sleep(times[4] * 1000);
				System.out.println(now() + tourName + " Reached Wuhan");
				barrier.await();
				System.out.println(now() + " All tour has reached Wuhan. over...");

			} catch (InterruptedException e) {
			} catch (BrokenBarrierException e) {
			}
		}
	}

	public static void main(String[] args) {
		// 三个旅行团
		CyclicBarrier barrier = new CyclicBarrier(3);
		ExecutorService exec = Executors.newFixedThreadPool(3);
		exec.submit(new Tour(barrier, "WalkTour", timeWalk));
		exec.submit(new Tour(barrier, "SelfTour", timeSelf));
		// 当我们把下面的这段代码注释后，会发现，程序阻塞了，无法继续运行下去。
		exec.submit(new Tour(barrier, "BusTour", timeBus));
		exec.shutdown();
	}
}