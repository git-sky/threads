package cn.com.sky.threads.concurrent.callablefuture;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 
 * 
 * ExecutorService中，execute与submit区别:
 * 
 * 1.execute没有返回值，submit有返回值Future。
 * 2.execute有一种参数Runnable，submit有两种参数Runnable与Callable。
 * 3.可以根据submit的返回值Future处理很多事情，捕获异常，取消任务等等。
 * 4.Future submit(Runnable task)，future.get()返回null。
 * 
 *  void execute(Runnable command);
 *  Future submit(Runnable task);
 *  Future submit(Callable task);
 * 
 * </pre>
 */
public class TestThreadPool2 extends Thread {
	private int index;

	public TestThreadPool2(int i) {
		this.index = i;
	}

	public void run() {
		try {
			System.out.println("[" + this.index + "] start....");
			Thread.sleep((int) (Math.random() * 1000));
			System.out.println("[" + this.index + "] end.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		ExecutorService service = Executors.newFixedThreadPool(4);
		for (int i = 0; i < 10; i++) {
			service.execute(new TestThreadPool2(i));
			service.submit(new TestThreadPool2(i));
		}
		System.out.println("submit finish");
		service.shutdown();
	}
}