package cn.com.sky.threads.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 
 * 在自旋锁中 有三种常见的锁形式:TicketLock ，CLHlock 和MCSlock
 * 
 * Ticket锁主要解决的是访问顺序的问题，主要的问题是在多核cpu上。
 * 
 * 每次都要查询一个serviceNum 服务号，影响性能（必须要到主内存读取，并阻止其他cpu修改）。
 * 
 * 
 * 
 * Ticket Lock 是为了解决公平性问题，类似于现实中银行柜台的排队叫号：锁拥有一个服务号，表示正在服务的线程，还有一个排队号；
 * 每个线程尝试获取锁之前先拿一个排队号，然后不断轮询锁的当前服务号是否是自己的排队号，如果是，则表示自己拥有了锁，不是则继续轮询。
 * 当线程释放锁时，将服务号加1，这样下一个线程看到这个变化，就退出自旋。
 * 
 * 
 * 缺点:
 * Ticket Lock 虽然解决了公平性的问题，但是多处理器系统上，每个进程/线程占用的处理器都在读写同一个变量serviceNum ，
 * 每次读写操作都必须在多个处理器缓存之间进行缓存同步，这会导致繁重的系统总线和内存的流量，大大降低系统整体的性能。
 * CLH锁和MCS锁都是为了解决这个问题的。
 * 
 * 
 * 该自旋锁不可重入。
 * 公平锁。
 *
 */
public class TicketLock implements SpinLockable {

	private AtomicInteger serviceNum = new AtomicInteger();// 服务号
	private AtomicInteger ticketNum = new AtomicInteger();// 排队号

	private static final ThreadLocal<Integer> LOCAL = new ThreadLocal<Integer>();

	public void lock() {
		int myticket = ticketNum.getAndIncrement();
		LOCAL.set(myticket);
		while (myticket != serviceNum.get()) {
		}

	}

	public void unlock() {
		int myticket = LOCAL.get();
		serviceNum.compareAndSet(myticket, myticket + 1);
	}
}