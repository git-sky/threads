package cn.com.sky.threads.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * test-test-and-set
 * <p>
 * TASLock算法的改进。
 * <p>
 * while (state.get()){}是一个改进，效果是先看一眼lock的状态，当lock是false时，
 * 再真正的执行state.getAndSet(true)。
 * 当state.getAndSet(true) 的return为false时，说明之前的确是false，于是获得锁，return。
 * 否则回到while(true),再次尝试获得锁。
 *
 * defect：
 * 在unlock时，state.set(false)还是会带来大量的cache miss。
 * cache miss VS cache hit
 */
public class TtasLock implements SpinLockable {

    AtomicBoolean state = new AtomicBoolean(false);

    public void lock() {
        while (true) {

            while (state.get()) {
            }

            if (!state.getAndSet(true)) {
                return;
            }

        }
    }

    public void unlock() {
        state.set(false);
    }
}
