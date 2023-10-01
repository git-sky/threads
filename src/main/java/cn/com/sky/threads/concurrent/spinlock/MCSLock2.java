package cn.com.sky.threads.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * MCSLock则是对本地变量的节点进行循环。
 */
public class MCSLock2 implements SpinLockable{

	public static class MCSNode {
		volatile boolean isLocked = true;
		volatile MCSNode next;
	}

	private static final ThreadLocal<MCSNode> NODE = new ThreadLocal<MCSNode>();
	@SuppressWarnings("unused")
	private volatile MCSNode queue;
	private static final AtomicReferenceFieldUpdater<MCSLock2, MCSNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(MCSLock2.class, MCSNode.class, "queue");

	public void lock() {
		MCSNode currentNode = new MCSNode();
		NODE.set(currentNode);
		MCSNode preNode = UPDATER.getAndSet(this, currentNode);
		if (preNode != null) {
			preNode.next = currentNode;
			while (currentNode.isLocked) {

			}
		}
	}

	public void unlock() {
		MCSNode currentNode = NODE.get();
		if (currentNode.next == null) {
			if (UPDATER.compareAndSet(this, currentNode, null)) {

			} else {
				while (currentNode.next == null) {
				}
			}
		} else {//??这段代码是有问题？？
			currentNode.next.isLocked = false;
			currentNode.next = null;
		}
	}
}
