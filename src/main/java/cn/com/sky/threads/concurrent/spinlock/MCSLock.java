package cn.com.sky.threads.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * MCS Spinlock
 * 是一种基于链表的可扩展、高性能、公平的自旋锁，申请线程只在本地变量上自旋，直接前驱负责通知其结束自旋，从而极大地减少了不必要的处理器缓存同步的次数，降低了总线和内存的开销。
 * 
 * 该锁不可重入。
 */
public class MCSLock {

	public static class MCSNode {
		volatile boolean isBlock = true; // 默认是在等待锁
		volatile MCSNode next;
	}

	 volatile MCSNode queue;// 指向最后一个申请锁的MCSNode

	private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(MCSLock.class, MCSNode.class, "queue");

	public void lock(MCSNode currentThread) {
		MCSNode predecessor = UPDATER.getAndSet(this, currentThread);// step 1
		if (predecessor != null) {
			predecessor.next = currentThread;// step 2

			while (currentThread.isBlock) {// step 3
			}
		}
	}

	public void unlock(MCSNode currentThread) {

		if (UPDATER.get(this) == currentThread) {// 锁拥有者进行释放锁才有意义
			if (currentThread.next == null) {// 检查是否有人排在自己后面
				if (UPDATER.compareAndSet(this, currentThread, null)) {// step 4
					// compareAndSet返回true表示确实没有人排在自己后面
					return;
				} else {
					// 突然有人排在自己后面了，可能还不知道是谁，下面是等待后续者
					// 这里之所以要忙等是因为：step 1执行完后，step 2可能还没执行完
					while (currentThread.next == null) { // step 5
					}
				}
			}

			currentThread.next.isBlock = false;
			currentThread.next = null;// for GC
		}

	}
}