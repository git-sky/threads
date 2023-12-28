package cn.com.sky.thread_juc.concurrent.locks.reentrantlock.model;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLockMethod {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                System.out.println(Thread.currentThread().getName() + " hasQueuedThreads=> " + lock.hasQueuedThreads());
                System.out.println(Thread.currentThread().getName() + " getHoldCount=> " + lock.getHoldCount());
                System.out.println(Thread.currentThread().getName() + " getQueueLength=> " + lock.getQueueLength());
                System.out.println(Thread.currentThread().getName() + " isLocked=》" + lock.isLocked());
                System.out.println(Thread.currentThread().getName() + " isFair=》" + lock.isFair());
                System.out.println(Thread.currentThread().getName() + " isHeldByCurrentThread=> " + lock.isHeldByCurrentThread());

                lock.unlock();
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                System.out.println(Thread.currentThread().getName() + " hasQueuedThreads=> " + lock.hasQueuedThreads());
                System.out.println(Thread.currentThread().getName() + " getHoldCount=> " + lock.getHoldCount());
                System.out.println(Thread.currentThread().getName() + " getQueueLength=> " + lock.getQueueLength());
                System.out.println(Thread.currentThread().getName() + " isLocked=》" + lock.isLocked());
                System.out.println(Thread.currentThread().getName() + " isFair=》" + lock.isFair());
                System.out.println(Thread.currentThread().getName() + " isHeldByCurrentThread=> " + lock.isHeldByCurrentThread());

                lock.unlock();
            }
        });

        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();

                System.out.println(Thread.currentThread().getName() + " hasQueuedThreads=> " + lock.hasQueuedThreads());
                System.out.println(Thread.currentThread().getName() + " getHoldCount=> " + lock.getHoldCount());
                System.out.println(Thread.currentThread().getName() + " getQueueLength=> " + lock.getQueueLength());
                System.out.println(Thread.currentThread().getName() + " isLocked=》" + lock.isLocked());
                System.out.println(Thread.currentThread().getName() + " isFair=》" + lock.isFair());
                System.out.println(Thread.currentThread().getName() + " isHeldByCurrentThread=> " + lock.isHeldByCurrentThread());

                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName() + " hasQueuedThreads=> " + lock.hasQueuedThreads());
        System.out.println(Thread.currentThread().getName() + " getHoldCount=> " + lock.getHoldCount());
        System.out.println(Thread.currentThread().getName() + " getQueueLength=> " + lock.getQueueLength());
        System.out.println(Thread.currentThread().getName() + " isLocked=》" + lock.isLocked());
        System.out.println(Thread.currentThread().getName() + " isFair=》" + lock.isFair());
        System.out.println(Thread.currentThread().getName() + " hasQueuedThread t1=> " + lock.hasQueuedThread(t1));
        System.out.println(Thread.currentThread().getName() + " hasQueuedThread t2=> " + lock.hasQueuedThread(t2));
        System.out.println(Thread.currentThread().getName() + " hasQueuedThread t3=> " + lock.hasQueuedThread(t3));
        System.out.println(Thread.currentThread().getName() + " isHeldByCurrentThread=> " + lock.isHeldByCurrentThread());
    }
}
