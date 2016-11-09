package cn.com.sky.threads.thread.synchronize.threelock;

public class LockTestClass {
	// 用于类锁计数
	private static int i = 0;
	// 私有锁
	private Object object = new Object();

	/**
	 * 无锁方法
	 */
	public void noSynMethod(long threadID, ObjThread thread) {
		System.out.println("nosyn: class obj is " + thread + ", threadId is" + threadID);
	}

	/**
	 * 对象锁方法1, synOnMethod
	 */
	public synchronized void synOnMethod() {
		System.out.println("synOnMethod begins" + ", time = " + System.currentTimeMillis() + "ms");
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("synOnMethod ends");
	}

	/**
	 * 对象锁方法2, synInMethod, 采用synchronized(this)加锁
	 */
	public void synInMethod() {
		synchronized (this) {
			System.out.println("synInMethod begins" + ", time = " + System.currentTimeMillis() + "ms");
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("synInMethod ends");
		}

	}

	/**
	 * 对象锁方法3, synMethodWithObj, 采用synchronized(object)加锁
	 */
	public void synMethodWithObj() {
		synchronized (object) {
			System.out.println("synMethodWithObj begins" + ", time = " + System.currentTimeMillis() + "ms");
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("synMethodWithObj ends");
		}
	}

	/**
	 * 类锁
	 */
	public static synchronized void increment() {
		System.out.println("class synchronized. i = " + i + ", time = " + System.currentTimeMillis() + "ms");
		i++;
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("class synchronized ends.");
	}

	/**
	 * 类锁2
	 */
	public static void increment2() {
		synchronized (LockTestClass.class) {
			System.out.println("class synchronized. i = " + i + ", time = " + System.currentTimeMillis() + "ms");
			i++;
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("class synchronized ends.");
		}
	}

	/**
	 * 类锁3
	 */
	public void increment3() {
		synchronized (LockTestClass.class) {
			System.out.println("class synchronized. i = " + i + ", time = " + System.currentTimeMillis() + "ms");
			i++;
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("class synchronized ends.");
		}
	}
}