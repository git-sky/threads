package cn.com.sky.threads.thread.synchronize.principle;

/**
 * <pre>
 * 
 * synchronized使用的场景很广，既能够锁住类方法（static），又能够锁住对象方法（非static）。既能够对某个属性加锁，又能够对this加锁。
 * 
 * Java的synchronized关键字锁的是一个对象的内置锁（被称为intrinsic lock 或 monitor lock）。
 * Synchronized Methods 和 Synchronized Statements的区别在于，前者自动帮你锁了当前调用方法的对象实例（若Static方法则锁类对象）。
 * 不像synchronized methods，synchronized statements必须指定提供intrinsic lock的对象。
 * 
 * 非static的synchronized方法和synchronized(this)用的是一个锁。
 * 
 * 实例对象锁和类的锁是不互影响的
 * 
 * 一.什么是sychronized
 * 
 * sychronized是java中最基本同步互斥的手段,可以修饰代码块,方法,类.
 * 
 * 在修饰代码块的时候需要一个reference对象作为锁的对象.
 * 
 * 在修饰方法的时候默认是当前对象作为锁的对象.
 * 
 * 在修饰类时候默认是当前类的Class对象作为锁的对象.
 * 
 * synchronized会在进入同步块的前后分别形成monitorenter和monitorexit字节码指令。
 * 在执行monitorenter指令时会尝试获取对象的锁,如果此对象没有被锁,或者此对象已经被当前线程锁住,那么锁的计数器加一,
 * 每当monitorexit被锁的对象的计数器减一.直到为0就释放该对象的锁。
 * 由此synchronized是可重入的,不会出现自己把自己锁死。
 * 
 * 
 * Thread.sleep(): 一个线程对象调用了sleep方法之后，并不会释放他所持有的所有对象锁。只会让出cpu。
 * 
 */
public class SyncTest {

	public SyncTest syncVar;

	public static SyncTest syncStaticVar;

	public static synchronized void testStaticSync() {

	}

	public synchronized void testNonStaticSync() {

	}

	public void testSyncThis() {
		synchronized (this) {//this指执行当前方法的实例对象,不是执行当前方法的线程。
			try {
				System.out.println("test sync this start");
				Thread.sleep(5000);
				System.out.println("test sync this end");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void testSyncVar() {
		synchronized (syncVar) {
			try {
				System.out.println("test sync var start");
				Thread.sleep(3000);
				System.out.println("test sync var end");
			} catch (InterruptedException e) {

			}
		}
	}

	public void testStaticSyncVar() {
		synchronized (syncStaticVar) {

		}
	}

	public static void main(String[] args) {

		final SyncTest testSync = new SyncTest();
//		 testSync.syncVar = new SyncTest();
		testSync.syncVar = testSync;// syncVar成员变量，指向了和this相同的区域。因此，在sychronized(this)和synchronized(syncVar)就形成了竞争，使得后者被阻塞。

		Thread threadOne = new Thread(new Runnable() {
			@Override
			public void run() {
				testSync.testSyncThis();
			}
		});

		Thread threadTwo = new Thread(new Runnable() {
			@Override
			public void run() {
				testSync.testSyncVar();
			}
		});

		threadOne.start();
		threadTwo.start();
	}
}
