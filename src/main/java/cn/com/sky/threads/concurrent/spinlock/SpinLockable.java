package cn.com.sky.threads.concurrent.spinlock;

public interface SpinLockable {

	void lock();

	void unlock();

}
