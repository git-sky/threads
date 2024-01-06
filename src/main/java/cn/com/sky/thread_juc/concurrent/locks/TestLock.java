package cn.com.sky.thread_juc.concurrent.locks;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * Lock接口的方法
 */
public class TestLock {

    public static void main(String[] args) {
        Lock lock = getLock();

        try {
            lock.lock();
            lock.lockInterruptibly();
            lock.tryLock();
            lock.tryLock(1, TimeUnit.MILLISECONDS);
            lock.unlock();
            lock.newCondition();
        } catch (Exception e) {

        }

    }

    private static Lock getLock() {
        Lock lock = new Lock() {
            @Override
            public void lock() {
                System.out.println("lock...");
            }

            @Override
            public void lockInterruptibly() throws InterruptedException {
                System.out.println("lockInterruptibly...");
            }

            @Override
            public boolean tryLock() {
                System.out.println("tryLock...");
                return false;
            }

            @Override
            public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
                System.out.println("tryLock time...");
                return false;
            }

            @Override
            public void unlock() {
                System.out.println("unlock...");
            }

            @Override
            public Condition newCondition() {
                System.out.println("newCondition...");
                return null;
            }
        };
        return lock;
    }
}
