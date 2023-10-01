package cn.com.sky.thread_juc.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <pre>
 *
 * 自旋锁
 *
 * 对于自旋锁来说，
 *
 * 1、若有同一线程两调用lock() ，会导致第二次调用lock位置进行自旋，产生了死锁,说明这个锁并不是可重入的。（在lock函数内，应验证线程是否为已经获得锁的线程）
 * 2、若1问题已经解决，当unlock（）第一次调用时，就已经将锁释放了。实际上不应释放锁。 （采用计数次进行统计）
 *
 * 该自旋锁为可重入锁。
 */
public class SpinLockReentrant implements SpinLockable {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    private int count = 0;

    public void lock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            count++;
            return;
        }

        while (!owner.compareAndSet(null, current)) {

        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            if (count != 0) {
                count--;
            } else {
                owner.compareAndSet(current, null);
            }

        }
    }

}
