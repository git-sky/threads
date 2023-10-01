package cn.com.sky.threads.thread.other_method;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 
 * 1) sleep()使当前线程进入停滞状态，所以执行sleep()的线程在指定的时间内肯定不会执行；
 * 	  yield()只是使当前线程重新回到可执行状态，所以执行yield()的线程有可能在进入到可执行状态后马上又被执行。
 * 2) sleep()可使优先级低的线程得到执行的机会，当然也可以让同优先级和高优先级的线程有执行的机会；
 *    yield()只能使同优先级的线程有执行的机会。
 * 
 * 
 * 
 * yield()方法是停止当前线程，让同等优先权的线程运行。如果没有同等优先权的线程，那么yield()方法将不会起作用。
 * 
 * 
 * 以下例子看不出来效果。。。
 * 
 * </pre>
 */
class TestThreadMethod extends Thread {

	// 定义匿名子类创建ThreadLocal的变量
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		// 覆盖初始化方法
		public Integer initialValue() {
			return 0;
		}
	};

	public void run() {

		System.out.println(Thread.currentThread().getName());

		while (true) {
            System.out.println("yield before :"+Thread.currentThread().getName() + ";seqNum:" + seqNum.get());
            Thread.yield();
            System.out.println("yield after :"+Thread.currentThread().getName() + ";seqNum:" + seqNum.get());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seqNum.set(seqNum.get() + 1);
//			System.out.println(Thread.currentThread().getName() + ";seqNum:" + seqNum.get());

		}
	}
}

public class TestThreadYield {

	public static void main(String[] args) {
		System.out.println(Runtime.getRuntime().availableProcessors());

		List<Thread> highThreads = new ArrayList<>();
		List<Thread> lowThreads = new ArrayList<>();

		TestThreadMethod t = new TestThreadMethod();

		int N=2;

		for (int i = 0; i < N; i++) {
			Thread highThread = new Thread(t, "High Thread-" + i);
			highThread.setPriority(Thread.MAX_PRIORITY);
			highThreads.add(highThread);
		}

		for (Thread thread : highThreads) {
			thread.start();
		}

//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

		for (int i = 0; i < N; i++) {
			Thread lowThread = new Thread(t, "Low Thread-" + i);
			lowThread.setPriority(Thread.MIN_PRIORITY);
			lowThreads.add(lowThread);
		}

		for (Thread thread : lowThreads) {
			thread.start();
		}

	}
}
