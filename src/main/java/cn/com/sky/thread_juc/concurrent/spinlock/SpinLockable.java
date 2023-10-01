package cn.com.sky.thread_juc.concurrent.spinlock;

public interface SpinLockable {

    void lock();

    void unlock();

}
