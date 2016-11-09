package cn.com.sky.threads.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

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