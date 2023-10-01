package cn.com.sky.threads.thread.producer_consumer;

/**
 * notify导致死锁
 */
public class TestDeadLockByNotify {

	public static void main(String[] args) {

		Goods goods = new Goods(1, 0);

		int threadNum = 2;
		int num = 2;

		int total = threadNum * num;

		for (int i = 0; i < threadNum; i++) {
			Producer p = new Producer(goods, 1, num);
			p.setName("P" + i);
			p.start();
		}

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Consumer c = new Consumer(goods, 1, total);
		c.setName("C");
		c.start();

	}
}

class Producer extends Thread {

	Goods goods;
	int num;
	int runNum;

	Producer(Goods goods, int num, int runNum) {
		this.goods = goods;
		this.num = num;
		this.runNum = runNum;
	}

	public void run() {
		for (int i = 0; i < runNum; i++) {
			goods.produce(num);
		}
	}

}

class Consumer extends Thread {

	Goods goods;
	int num;
	int runNum;

	Consumer(Goods goods, int num, int runNum) {
		this.goods = goods;
		this.num = num;
		this.runNum = runNum;
	}

	public void run() {
		for (int i = 0; i < runNum; i++) {
			goods.consume(num);
		}
	}

}

class Goods {

	int totalSpace;
	int usedSpace;

	Goods(int totalSpace, int usedSpace) {
		this.totalSpace = totalSpace;
		this.usedSpace = usedSpace;
	}

	public synchronized void produce(int num) {
		System.out.println(Thread.currentThread().getName() + ": get lock .......");

		while (usedSpace + num > totalSpace) {
			try {
				System.out.println(Thread.currentThread().getName() + ": sleep and relase lock .......");
				wait();
				System.out.println(Thread.currentThread().getName() + ": waked up and get lock  ....");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		usedSpace += num;
		System.out.println(Thread.currentThread().getName() + ": relase lock and wake a thread .......");
		notify();

	}

	public synchronized void consume(int num) {

		System.out.println(Thread.currentThread().getName() + ": get lock .......");

		while (usedSpace - num < 0) {
			try {
				System.out.println(Thread.currentThread().getName() + ": sleep and relase lock .......");
				wait();
				System.out.println(Thread.currentThread().getName() + ": waked up and get lock .......");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		usedSpace -= num;
		System.out.println(Thread.currentThread().getName() + ": relase lock and wake a thread .......");
		notify();
	}

}
