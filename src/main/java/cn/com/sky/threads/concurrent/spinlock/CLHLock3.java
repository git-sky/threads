package cn.com.sky.threads.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CLH lock queue其实就是一个FIFO的队列，队列中的每个结点（线程）只要等待其前继释放锁就可以了。
 */
public class CLHLock3 implements SpinLockable{

	private final ThreadLocal<Node> prevLocal;
	private final ThreadLocal<Node> nodeLocal;
	private final AtomicReference<Node> tailRef = new AtomicReference<Node>(new Node());

	public static void main(String[] args) {
		
		final CLHLock3 lock = new CLHLock3();
		
//		lock.lock();

		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " wait for lock....");
					lock.lock();
					 try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + " acquired the lock!");
					lock.unlock();
					System.out.println(Thread.currentThread().getName() + " after unlock ......");
				}
			}).start();
			// Thread.sleep(100);
		}

//		System.out.println("main thread unlock!");
//		lock.unlock();
	}

	public CLHLock3() {
		this.nodeLocal = new ThreadLocal<Node>() {
			protected Node initialValue() {
				return new Node();
			}
		};

		this.prevLocal = new ThreadLocal<Node>() {
			protected Node initialValue() {
				return null;
			}
		};
	}

	public void lock() {
		final Node node = this.nodeLocal.get();
		node.locked = true;
		// 一个CAS操作即可将当前线程对应的节点加入到队列中，
		// 并且同时获得了前继节点的引用，然后就是等待前继释放锁
		Node pred = this.tailRef.getAndSet(node);
		this.prevLocal.set(pred);
		while (pred.locked) {// 进入自旋
		}
	}

	public void unlock() {
		final Node node = this.nodeLocal.get();
		node.locked = false;
		this.nodeLocal.set(this.prevLocal.get());
	}

	private static class Node {
		private volatile boolean locked;
	}
}