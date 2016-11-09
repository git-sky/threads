package cn.com.sky.threads.concurrent.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 
 * Java线程：锁
 * 
 * 二.什么ReentrantLock
 * 
 * 以对象的方式来操作对象锁,相对于sychronized,需要在finally中去释放锁
 * 
 * 三.synchronized和ReentrantLock的区别
 * 
 * 
 * synchronized局限：
 * 1.不能中断一个正在试图获得锁的线程。
 * 2.试图获得锁时不能设定超时。
 * 3.每个锁只有一个条件，有时候是不够的。
 * 
 * 
 * 
 * 除了synchronized的功能,多了三个高级功能.
 * 
 * 等待可中断,公平锁,绑定多个Condition.
 * 
 * 1.等待可中断
 * 
 * 在持有锁的线程长时间不释放锁的时候,等待的线程可以选择放弃等待. tryLock(long timeout, TimeUnit unit)
 * 
 * 2.公平锁
 * 
 * 按照申请锁的顺序来一次获得锁称为公平锁.synchronized的是非公平锁,ReentrantLock可以通过构造函数实现公平锁. new RenentrantLock(boolean
 * fair)
 * 
 * 3.绑定多个Condition
 * 
 * 通过多次newCondition可以获得多个Condition对象,可以简单的实现比较复杂的线程同步的功能.通过await(),signal();
 * 
 * 
 * 
 * 总结来说，Lock和synchronized有以下几点不同：
 * 
 * 　　1）Lock是一个接口，而synchronized是Java中的关键字，synchronized是内置的语言实现；
 * 
 * 　　2）synchronized在发生异常时，会自动释放线程占有的锁，因此不会导致死锁现象发生；而Lock在发生异常时，如果没有主动通过unLock()去释放锁，则很可能造成死锁现象，因此使用Lock时需要在finally块中释放锁；
 * 
 * 　　3）Lock可以让等待锁的线程响应中断，而synchronized却不行，使用synchronized时，等待的线程会一直等待下去，不能够响应中断；
 * 
 * 　　4）通过Lock可以知道有没有成功获取锁，而synchronized却无法办到。
 * 
 * 　　5）Lock可以提高多个线程进行读操作的效率。
 * </pre>
 * 
 */
public class TestThreadLock {
	public static void main(String[] args) {
		// 创建并发访问的账户
		MyCountCard myCount = new MyCountCard("95599200901215522", 10000);
		// 创建一个锁对象
		Lock lock = new ReentrantLock();
		// 创建一个线程池
		ExecutorService pool = Executors.newCachedThreadPool();
		// 创建一些并发访问用户，一个信用卡，存的存，取的取，好热闹啊
		MyUser u1 = new MyUser("张三", myCount, -4000, lock);
		MyUser u2 = new MyUser("张三他爹", myCount, 6000, lock);
		MyUser u3 = new MyUser("张三他弟", myCount, -8000, lock);
		MyUser u4 = new MyUser("张三", myCount, 800, lock);

		// 在线程池中执行各个用户的操作
		pool.execute(u1);
		pool.execute(u2);
		pool.execute(u3);
		pool.execute(u4);
		// 关闭线程池
		pool.shutdown();
	}
}

/**
 * 信用卡的用户
 */
class MyUser implements Runnable {
	private String name; // 用户名
	private MyCountCard myCount; // 所要操作的账户
	private int iocash; // 操作的金额，当然有正负之分了
	private Lock myLock; // 执行操作所需的锁对象

	MyUser(String name, MyCountCard myCount, int iocash, Lock myLock) {
		this.name = name;
		this.myCount = myCount;
		this.iocash = iocash;
		this.myLock = myLock;
	}

	public void run() {
		// 获取锁
		myLock.lock();
		// 执行现金业务
		System.out.println(name + "正在操作" + myCount + "账户，金额为" + iocash + "，当前金额为" + myCount.getCash());
		myCount.setCash(myCount.getCash() + iocash);
		System.out.println(name + "操作" + myCount + "账户成功，金额为" + iocash + "，当前金额为" + myCount.getCash());
		// 释放锁，否则别的线程没有机会执行了
		myLock.unlock();
	}
}

/**
 * 信用卡账户，可随意透支
 */
class MyCountCard {
	private String oid; // 账号
	private int cash; // 账户余额

	MyCountCard(String oid, int cash) {
		this.oid = oid;
		this.cash = cash;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	@Override
	public String toString() {
		return "MyCountCard{" + "oid='" + oid + '\'' + ", cash=" + cash + '}';
	}
}