package cn.com.sky.thread_juc.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * <pre>
 *
 * CLHLock 和 MCSLock 则是两种类型相似的公平锁，采用链表的形式进行排序。
 *
 * CLHlock是不停的查询前驱变量, 导致不适合在NUMA 架构下使用（在这种结构下，每个线程分布在不同的物理内存区域）。
 *
 * MCSLock则是对本地变量的节点进行循环。不存在CLHlock 的问题。
 *
 * 从代码上 看，CLH 要比 MCS 更简单，
 *
 * CLH 的队列是隐式的队列，没有真实的后继结点属性。
 *
 * MCS 的队列是显式的队列，有真实的后继结点属性。
 *
 * ReentrantLock 默认内部使用的锁 即是 CLH锁（有很多改进的地方，将自旋锁换成了阻塞锁等等）。
 */
public class CLHLock implements SpinLockable {

    public static class CLHNode {
        private volatile boolean isLocked = true;// 默认是在等待锁
    }

    private volatile CLHNode tail;

    private static final ThreadLocal<CLHNode> LOCAL = new ThreadLocal<>();

    //原子更新对象的引用类型的字段。
    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock() {
        CLHNode node = new CLHNode();
        LOCAL.set(node);
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {
            while (preNode.isLocked) {
            }
            preNode = null;
            LOCAL.set(node);
        }
    }

    public void unlock() {
        CLHNode node = LOCAL.get();
        if (!UPDATER.compareAndSet(this, node, null)) {
            node.isLocked = false;
        }
        node = null;
    }
}
