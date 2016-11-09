package cn.com.sky.threads.concurrent.spinlock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * test-test-and-set
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
