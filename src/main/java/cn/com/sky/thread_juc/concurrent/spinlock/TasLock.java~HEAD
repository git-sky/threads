package cn.com.sky.thread_juc.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * test-and-set
 * <p>
 * 每一个Lock带有一个状态位，lock()与unlock()操作原子的改变状态位。
 * false时可进入，true时spin。
 * <p>
 * defect：
 * 在锁被其他线程持有的情况下，while(state.getAndSet(true))会不停的将state从true改为true。
 */
public class TasLock implements SpinLockable {

    AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        while (state.getAndSet(true)) {
        }
    }

    public void unlock() {
        state.set(false);
    }
}
