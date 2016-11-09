package cn.com.sky.threads.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * test-and-set
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
