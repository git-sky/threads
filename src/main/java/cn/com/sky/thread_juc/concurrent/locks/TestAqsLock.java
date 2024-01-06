package cn.com.sky.thread_juc.concurrent.locks;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;

/**
 * <pre>
 * AQS核心的8个核心方法(按照独占锁和共享锁划分)，以及使用类。
 *
 * 独占锁的四个核心方法
 * acquire
 * acquireInterruptibly
 * tryAcquireNanos
 * release();
 *
 * 独占锁
 * ReentrantLock
 * ReentrantReadWriteLock 的 WriteLock
 *
 * 共享锁的四个核心方法
 * acquireShared
 * acquireSharedInterruptibly
 * tryAcquireSharedNanos
 * releaseShared
 *
 * 共享锁
 * ReentrantReadWriteLock 的 ReadLock
 * Semphore
 *
 * </pre>
 */
public class TestAqsLock {

    public static void main(String[] args) {
        AbstractQueuedSynchronizer aqs = getAqs();
        Lock lock = new ReentrantLock();
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        Semaphore semaphore = new Semaphore(1);
        try {
            //独占锁
            lock.lock();
            readWriteLock.writeLock().lock();
            aqs.acquire(1);

            lock.lockInterruptibly();
            readWriteLock.writeLock().lockInterruptibly();
            aqs.acquireInterruptibly(1);

            lock.tryLock(1, TimeUnit.MILLISECONDS);
            readWriteLock.writeLock().tryLock(1, TimeUnit.MILLISECONDS);
            aqs.tryAcquireNanos(1, TimeUnit.MICROSECONDS.toNanos(1));

            lock.unlock();
            readWriteLock.writeLock().unlock();
            aqs.release(1);

            //共享锁

            readWriteLock.readLock().lock();
            semaphore.acquireUninterruptibly();
            aqs.acquireShared(1);

            readWriteLock.readLock().lockInterruptibly();
            semaphore.acquire();
            aqs.acquireSharedInterruptibly(1);

            readWriteLock.readLock().tryLock(1, TimeUnit.MICROSECONDS);
            semaphore.tryAcquire(1, TimeUnit.MICROSECONDS);
            aqs.tryAcquireSharedNanos(1, TimeUnit.MICROSECONDS.toNanos(1));

            readWriteLock.readLock().unlock();
            semaphore.release();
            aqs.releaseShared(1);

        } catch (Exception e) {

        }
    }

    private static AbstractQueuedSynchronizer getAqs() {
        AbstractQueuedSynchronizer abstractQueuedSynchronizer = new AbstractQueuedSynchronizer() {
            @Override
            protected boolean tryAcquire(int arg) {
                return super.tryAcquire(arg);
            }

            @Override
            protected boolean tryRelease(int arg) {
                return super.tryRelease(arg);
            }

            @Override
            protected int tryAcquireShared(int arg) {
                return super.tryAcquireShared(arg);
            }

            @Override
            protected boolean tryReleaseShared(int arg) {
                return super.tryReleaseShared(arg);
            }
        };

        return abstractQueuedSynchronizer;
    }

}
