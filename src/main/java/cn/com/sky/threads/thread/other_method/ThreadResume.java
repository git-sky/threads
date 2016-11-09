package cn.com.sky.threads.thread.other_method;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * <pre>
 * 
 * 说明: 
 * 调用了suspend方法，将t0线程挂起，但是出现的问题是，t0.suspend方法之后的代码不执行了，搞了半天终于知道为什么了， 
 * 因为在t0里面使用了System.out.println方法了，查看println方法的源代码发现他是个同步的方法，加锁了，这个锁是哪个呢？ 
 * 对就是PrintStream,在Main中的printCurrentAliveThread方法中用到了System.out.println方法，打断点才知道 
 * 搞了半天阻塞在这里了，因为我们知道suspend方法是不释放锁的，所以导致会阻塞在println方法中，但是有一个前提是t0线程和main线程 
 * 的println方法中拿到的是同一个锁，这时候在看一下System.out变量时一个static PrintStream，这时候就明了了，因为是static
 * 所以对象是相同的，这两个线程拿到的System.out是同一个对象，所以这两个线程也是拿到的是相同的锁的。
 * 
 * 
 * suspend挂起线程，但是不释放锁。
 * suspend/resume容易导致死锁，所以弃用了。
 * 
 * 
 */
public class ThreadResume {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		try {
			// 定义线程
			Thread t0 = new Thread() {
				public void run() {
					try {
						for (long i = 0; i < 1000 * 1000 * 10; i++) {
							System.out.println(i);
						}
						System.out.println("thread death");
					} catch (Throwable ex) {
						System.out.println("Caught in run: " + ex);
						ex.printStackTrace();
					}
				}
			};
			// 开启线程
			t0.start();
			// 等待2s之后挂起线程t0
			Thread.sleep(2 * 1000);
			// 挂起线程
			t0.suspend();
			// 打印当前的所有线程
			printCurrentAliveThread();
			// 等待2s之后恢复线程
			Thread.sleep(2 * 1000);
			// 复活线程
			t0.resume();

		} catch (Throwable t) {
			System.out.println("Caught in main: " + t);
			t.printStackTrace();
		}

	}

	/**
	 * 打印当前线程
	 */
	public static void printCurrentAliveThread() {
		Map<Thread, StackTraceElement[]> maps = Thread.getAllStackTraces();
		Set<Entry<Thread, StackTraceElement[]>> set = maps.entrySet();
		Iterator<Entry<Thread, StackTraceElement[]>> iterator = set.iterator();
		System.out.println("System Alive Thread List:");
		System.out.println("-------------------------");
		while (iterator.hasNext()) {
			System.out.println("AliveThread_Name:" + iterator.next().getKey().getName());
		}
		System.out.println("-------------------------");
	}
}
