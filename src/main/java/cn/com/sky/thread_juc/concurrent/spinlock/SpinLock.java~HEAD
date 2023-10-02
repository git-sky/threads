package cn.com.sky.thread_juc.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *
 * 1、自旋锁
 *
 * 自旋锁是采用让当前线程不停地的在循环体内执行实现的，当循环的条件被其他线程改变时 才能进入临界区。
 *
 * 如下例子使用了CAS原子操作，lock函数将owner设置为当前线程，并且预测原来的值为空。unlock函数将owner设置为null，并且预测值为当前线程。
 *
 * 当有第二个线程调用lock操作时由于owner值不为空，导致循环一直被执行，直至第一个线程调用unlock函数将owner设置为null，第二个线程才能进入临界区。
 *
 * 由于自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。
 * 但当线程数不停增加时，性能下降明显，因为每个线程都需要执行，占用CPU时间。如果线程竞争不激烈，
 * 并且保持锁的时间短。适合使用自旋锁。
 *
 * 注：该例子为非公平锁，获得锁的先后顺序，不会按照进入lock的先后顺序进行。
 *
 * 该自旋锁不可重入。
 * 非公平锁。
 *
 * 不可重入非公平锁。
 */
public class SpinLock implements SpinLockable {

    private AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        // 如果锁未被占用，则设置当前线程为锁的拥有者
        while (!owner.compareAndSet(null, current)) {
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        // 只有锁的拥有者才能释放锁
        owner.compareAndSet(current, null);
    }
}