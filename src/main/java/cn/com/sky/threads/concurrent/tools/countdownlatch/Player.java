package cn.com.sky.threads.concurrent.tools.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * <pre>
 * 
 * 使用CountDownLatch进行异步转同步操作，每个线程退出前必须调用countDown方法，线程执行代码注意catch异常，
 * 确保countDown方法可以执行，避免主线程无法执行至countDown方法，直到超时才返回结果。 
 * 说明：注意，子线程抛出异常堆栈，不能在主线程try-catch到。
 * 
 * </pre>
 */
public class Player implements Runnable {

	private int id;
	private CountDownLatch begin;
	private CountDownLatch end;

	public Player(int i, CountDownLatch begin, CountDownLatch end) {
		super();
		this.id = i;
		this.begin = begin;
		this.end = end;
	}

	@Override
	public void run() {
		try {
			begin.await(); // 等待begin的状态为0，即等待开始比赛。
			Thread.sleep((long) (Math.random() * 100)); // 随机分配时间，即运动员完成时间
			System.out.println("Play" + id + " arrived.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			end.countDown(); // 使end状态减1，最终减至0
		}
	}
}