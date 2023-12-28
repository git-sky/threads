package cn.com.sky.thread_juc.concurrent.locks.reentrantlock.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *
 * 当一个线程获取了锁之后，是不会被interrupt()方法中断的。
 * 单独调用interrupt()方法不能中断正在运行过程中的线程，只能中断阻塞过程中的线程。
 *
 * lockInterruptibly() 当通过这个方法去获取锁时，如果线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态。
 * 当通过lockInterruptibly()方法获取某个锁时，如果不能获取到，只有进行等待的情况下，是可以响应中断的。
 * 如果获取到锁了，则不能响应中断了。
 *
 * 而用synchronized修饰的话，当一个线程处于等待某个锁的状态，是无法被中断的，只有一直等待下去。
 *
 * 简而言之：
 * 等待锁时，lockInterruptibly可以响应中断，synchronized不能响应中断。
 *
 * 获得锁之后，都不能响应中断 。
 */
public class TestLockInterruptly {

    public static void main(String[] args) {

    }

    public void method() throws InterruptedException {
        Lock lock = new ReentrantLock();

        /**
         * lockInterruptibly跟lock相比，只是多了中断后抛出异常逻辑，其他大体相同的。
         *
         * 可中断锁逻辑：
         * 有中断标记：则抛出中断异常。
         * 无中断标记：
         * 公平锁，判断是否等待队列有等待节点，有则抢锁失败。
         * 非公平锁，直接抢锁，如果抢锁失败。
         * =》如果公平锁或者非公平锁抢锁失败 => 则创建节点并放到队尾
         * => 循环判断：
         *  如果是公平锁：本节点是否为等待队列的第一个节点，
         *  是的话，则尝试获取锁（等待队列有节点则获取失败）。
         *  不是的话，则更新前继节点的waitStatus为signal，更新成功后，park当前线程。
         *  被中断后抛出中断异常。
         *
         *  如果是非公平锁：本节点是否为等待队列的第一个节点，
         *  是的话，则尝试获取锁（不判断等待队列是否有节点，直接抢锁）。
         *  不是的话，则更新前继节点的waitStatus为signal，更新成功后，park当前线程。
         *  被中断后抛出中断异常。
         */
        lock.lockInterruptibly();
        try {
            //to do something
        } finally {
            lock.unlock();
        }
    }
}
