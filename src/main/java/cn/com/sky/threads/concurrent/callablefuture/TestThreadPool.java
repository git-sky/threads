package cn.com.sky.threads.concurrent.callablefuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * <pre>
 * 
 * Java线程：线程池
 * 
 * ExecutorService中，execute与submit区别:
 * 
 * 1.execute没有返回值，submit有返回值Future。
 * 2.execute有一种参数Runnable，submit有两种参数Runnable与Callable。
 * 3.可以根据submit的返回值Future处理很多事情，捕获异常，取消任务等等。
 * 4.Future submit(Runnable task)，future.get()返回null。
 * 
 * 方法定义如下：
 *  1. void execute(Runnable command);
 *  
 *  2. Future submit(Runnable task);
 *    执行流程：
 *    a> submit(Runnable task)--->new FutureTask(Runnable task,T value)--->new Sync(Executors.callable(runnable, result))--> new RunnableAdapter<T>(task, result)-->task.run();
 *    b> execute(Runnable command)
 *               
 *  
 *  3. Future submit(Callable task);
 *    执行流程：
 *    a> submit(Runnable task)--->new FutureTask<T>(callable)---> new Sync(callable)
 *    b> execute(Runnable command)
 *    
 *    
 * 本质上最后都是调用的execute(Runnable command)方法。
 * 
 *  Callable执行本质上还是被Thread类的run方法调用执行。
 * 
 * 
 * shutdown()
 * 启动一次顺序关闭，执行以前提交的任务，但不接受新任务。如果已经关闭，则调用没有其他作用。 
 * 
 * 
 * shutdownNow()
 * 阻止等待任务的启动并试图停止当前正在执行的任务。
 * 
 */
public class TestThreadPool {
	public static void main(String[] args) {
		// 创建一个可重用固定线程数的线程池
//		 ExecutorService pool = Executors.newFixedThreadPool(2);
		//
		// //创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。
		// ExecutorService pool = Executors.newSingleThreadExecutor();
		//
		// 创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
		// ExecutorService pool = Executors.newCachedThreadPool();

		ThreadPoolExecutor pool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		Thread t1 = new MyThreadTest("t1");
		Thread t2 = new MyThreadTest("t2");
		Thread t3 = new MyThreadTest("t3");
		Thread t4 = new MyThreadTest("t4");
		Thread t5 = new MyThreadTest("t5");
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);

		// 关闭线程池
		pool.shutdown();
	}
}

class MyThreadTest extends Thread {

	MyThreadTest(String name) {
		super(name);
	}

	private List<Object> list = new ArrayList<>();

	@Override
	public void run() {

		for (int i = 0; i < 10000000; i++) {
			list.add(new Object());
			// System.out.println(Thread.currentThread().getName() + "正在执行。。。");
		}
		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
	}
}