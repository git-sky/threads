package cn.com.sky.threads.concurrent.cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * <pre>
 * 
 * CyclicBarrier是一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier
 * point)。在涉及一组固定大小的线程的程序中，这些线程必须不时地互相等待，此时 CyclicBarrier 很有用。因为该 barrier
 * 在释放等待线程后可以重用，所以称它为循环 的 barrier。 CyclicBarrier类似于CountDownLatch也是个计数器，
 * 不同的是CyclicBarrier数的是调用了CyclicBarrier.await()进入等待的线程数，
 * 当线程数达到了CyclicBarrier初始时规定的数目时，所有进入等待状态的线程被唤醒并继续。
 * CyclicBarrier就象它名字的意思一样，可看成是个障碍， 所有的线程必须到齐后才能一起通过这个障碍。
 * CyclicBarrier初始时还可带一个Runnable的参数 ，此Runnable任务在CyclicBarrier的数目达到后，所有其它线程被唤醒前被执行。
 * 
 * 
 * 在某种需求中，比如一个大型的任务，常常需要分配好多子任务去执行，只有当所有子任务都执行完成时候，才能执行主任务，这时候，
 * 就可以选择CyclicBarrier了。
 * 
 * //设置parties、count及barrierCommand属性。
 * CyclicBarrier(int)
 * 
 * //当await的数量到达了设定的数量后，首先执行该Runnable对象。
 * CyclicBarrier(int,Runnable)
 * 
 * //通知barrier已完成线程
 * await()
 * 
 * </pre>
 */
public class TestCyclicBarrier3 {
	public static void main(String[] args) {
		// 创建障碍器，并设置MainTask为所有定数量的线程都达到障碍点时候所要执行的任务(Runnable)
		CyclicBarrier cb = new CyclicBarrier(7, new MainTask());
		new SubTask("A", cb).start();
		new SubTask("B", cb).start();
		new SubTask("C", cb).start();
		new SubTask("D", cb).start();
		new SubTask("E", cb).start();
		new SubTask("F", cb).start();
		new SubTask("G", cb).start();
	}
}

/**
 * 主任务
 */
class MainTask implements Runnable {
	public void run() {
		System.out.println(">>>>主任务执行了！<<<<");
	}
}

/**
 * 子任务
 */
class SubTask extends Thread {
	private String name;
	private CyclicBarrier cb;

	SubTask(String name, CyclicBarrier cb) {
		this.name = name;
		this.cb = cb;
	}

	public void run() {
		System.out.println("[子任务" + name + "]开始执行了！");
		for (int i = 0; i < 999999999; i++)
			; // 模拟耗时的任务
		System.out.println("[子任务" + name + "]开始执行完成了，并通知障碍器已经完成！");
		try {
			// 通知障碍器已经完成
			cb.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
}