package cn.com.sky.thread_juc.concurrent.locks.park;

import org.junit.Test;

import java.util.concurrent.locks.LockSupport;

/**
 * park的返回时机，除了下面的四种，有时候会无理由的返回。
 *
 * 返回时机：
 * 1.unpark
 * 2.中断
 * 3.超时（绝对时间、相对时间）
 * 4.无理由返回
 */
public class TestLockSupportParkReturn {

	/**
	 * 1.针对当前线程已经调用过unpark(多次调用unpark的效果和调用一次unpark的效果一样),park方法马上返回.
	 */
	@Test
	public void test_unpark() {

		Thread currThread = Thread.currentThread();

		LockSupport.unpark(currThread);
		System.out.println("unpark one ");
		LockSupport.unpark(currThread);
		System.out.println("unpark two ");
		LockSupport.unpark(currThread);
		System.out.println("unpark three ");

		LockSupport.park();
		System.out.println("park one ");
//		 LockSupport.park();
//		 System.out.println("park two ");
	}

	/**
	 * 2.在当前线程中断的时候或者调用unpark的时候返回。
	 */
	@Test
	public void test_interrupt() {

		final Thread currThread = Thread.currentThread();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(3000);
					 currThread.interrupt();
//					LockSupport.unpark(currThread);
				} catch (Exception e) {
				}
			}
		}.start();

		LockSupport.park();
        System.out.println("park one");
        LockSupport.park();
        System.out.println("park two");
        LockSupport.park();

		System.out.println("中断返回。。。");
	}

	/**
	 * 3.如果是相对时间也就是isAbsolute为false（注意这里后面的单位纳秒）到期的时候返回.
	 */
	@Test
	public void test_timeout() {

		// 相对时间后面的参数单位是纳秒
		// unsafe.park(false, 3000000000l);
		LockSupport.parkNanos(3000000000l);

		System.out.println("SUCCESS!!!");
	}

	/**
	 * 4.如果是绝对时间也就是isAbsolute为true(注意后面的单位是毫秒)到期的时候返回。
	 */
	@Test
	public void test_absolute_timeout() {

		long time = System.currentTimeMillis() + 3000;

		// 绝对时间后面的参数单位是毫秒
		// unsafe.park(true, time);

		LockSupport.parkUntil(time);

		System.out.println("SUCCESS!!!");
	}

}