package cn.com.sky.threads.thread.synchronize;

/**
 * <pre>
 * 
 * 对象锁：
 * 持有同一个对象锁，才会有加锁的效果，两个对象的对象锁没有关系。
 *  
 *  假设有一个类ClassA，其中有一个方法synchronized methodA()，那么当这个方法被调用的时候获得就是对象锁，
 *  但是要注意，如果这个类有两个实例，
 *  比如：
 *  ClassA a = new ClassA(); 
 *  ClassA b = new ClassA();
 *  那么如果你在a这对象上调用了methodA，不会影响b这个对象，也就是说对于b这个对象，他也可以调用methodA，
 *  因为这是两对象，所以说对象锁是针对对象的。
 * </pre>
 */
class TestSynchronized extends Thread {
	public TestSynchronized(String name) {
		super(name);
	}

	public synchronized void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + " : " + i);
			try {
				Thread.sleep(100);//sleep不释放锁
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
	}
}

public class TestThreadSynchronized {
	public static void main(String[] args) {
		TestSynchronized t1 = new TestSynchronized("t1");
		TestSynchronized t2 = new TestSynchronized("t2");
		t1.start();
		// t1.start();////线程只能启动一次,再次启动会爆IllegalThreadStateException异常。
		t2.start();// // 由于t1和t2是两个对象，所以它们所启动的线程可同时访问run()函数。

	}
}