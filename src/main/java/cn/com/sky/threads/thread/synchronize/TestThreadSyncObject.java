package cn.com.sky.threads.thread.synchronize;

/**
 * <pre>
 * 
 * ThreadAA和ThreadBB使用lockObj对象锁。
 * 
 * 注意： 当在对象上调用wait()方法时，执行该代码的线程立即放弃它在对象上的锁。 然而调用notify()时，并不意味着这时线程会放弃其锁。
 * 如果线程仍然在完成同步代码，则线程在移出之前不会放弃锁。 因此，只要调用notify()并不意味着这时该锁变得可用。 wait() 必须在synchronized 函数或者代码块里面。
 * 
 * [wait(),notify()/notityAll()方法是普通对象的方法(Object超类中实现),而不是线程对象的方法]
 * [wait(),notify()/notityAll()方法只能在同步方法中调用]
 * 
 * 线程A： 循环50次后等待并放弃锁，让线程B执行。
 * </pre>
 */
class ThreadAA extends Thread {
	// 线程同步的公共数据区
	Object oa = null;

	ThreadAA(Object o) {
		this.oa = o;
	}

	// 线程A执行逻辑
	public void run() {
		// 线程同步区域，需要申请公共数据的锁
		synchronized (oa) {
			System.out.println("ThreadAA is running......");
			for (int i = 0; i < 100; i++) {
				System.out.println("   ThreadAA value is " + i);
				if (i == 50) {
					try {
						// 当前线程等待
						oa.wait();// Thread.currentThread().wait();//错误
						System.out.println("ThreadAA继续执行..................");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

/**
 * 线程B：等待线程A放弃锁，然后获得锁并执行，完成后唤醒线程A
 */
class ThreadBB extends Thread {
	// 线程同步的公共数据区
	Object ob = null;

	ThreadBB(Object o) {
		this.ob = o;
	}

	// 线程B执行逻辑
	public void run() {
		// 线程同步区域，需要申请公共数据的锁
		synchronized (ob) {
			System.out.println("ThreadBB is running......");
			for (int i = 0; i < 50; i++) {
				System.out.println("   ThreadBB value is " + i);
			}
			// 唤醒等待的线程;调用notify()时，并不意味着这时线程会放弃其锁。 如果线程仍然在完成同步代码，则线程在移出之前不会放弃锁。
			ob.notify();// notify();//错误,应该唤醒在等待ob上的线程

			for (int i = 0; i < 100000; i++) {
				System.out.println(i);
			}
		}
	}
}

public class TestThreadSyncObject {
	public static void main(String[] args) {
		Object lockObj = new Object(); // 公共数据区,对象锁是lockObj
		ThreadAA threada = new ThreadAA(lockObj);
		ThreadBB threadb = new ThreadBB(lockObj);

		threada.start(); // 线程A执行
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		threadb.start(); // 线程B执行

	}
}
