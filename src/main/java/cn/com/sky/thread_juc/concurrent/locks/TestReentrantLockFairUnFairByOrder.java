package cn.com.sky.thread_juc.concurrent.locks;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *
 * 在非公平获取的过程中，“插队”现象非常严重，后续获取锁的线程根本不顾及sync队列中等待的线程，而是能获取就获取。
 * 反观公平获取的过程，锁的获取就类似线性化的，每次都由sync队列中等待最长的线程（链表的第一个，sync队列是由尾部结点添加，当前输出的sync队列是逆序输出）获取锁。
 */
public class TestReentrantLockFairUnFairByOrder {
    private static Lock fairLock = new ReentrantLock2(true);
    private static Lock unfairLock = new ReentrantLock2();

    @Test
    public void fair() {
        System.out.println("fair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(fairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void unfair() {
        System.out.println("unfair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(unfairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Job implements Runnable {
        private Lock lock;

        public Job(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                lock.lock();
                try {
                    System.out.println("Lock by:" + Thread.currentThread().getName() + " and " + ((ReentrantLock2) lock).getQueuedThreads() + " waits.");
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private static class ReentrantLock2 extends ReentrantLock {

        private static final long serialVersionUID = 1773716895097002072L;

        public ReentrantLock2() {
            super();
        }

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }
}