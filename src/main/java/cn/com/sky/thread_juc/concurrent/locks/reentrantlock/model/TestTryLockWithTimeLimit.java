package cn.com.sky.thread_juc.concurrent.locks.reentrantlock.model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestTryLockWithTimeLimit {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();

        /**
         * 有中断标记：则抛出中断异常。
         * 无中断标记：
         * 公平锁，判断是否等待队列有等待节点，有则抢锁失败。
         * 非公平锁，直接抢锁，如果抢锁失败。
         * 抢锁失败之后：
         * 如果超时，则返回。
         * 未超时，则创建节点并放到队尾。
         * ==> 循环判断：
         * *  如果是公平锁：本节点是否为等待队列的第一个节点，
         * *  是的话，则尝试获取锁（等待队列有节点则获取失败）。
         * *  不是的话，判断是否过期，未过期则更新前继节点的waitStatus为signal，更新成功后，如果剩余时间大于1000ns，parkNanos当前线程。
         * *  被中断后抛出中断异常。
         * *
         * *  如果是非公平锁：本节点是否为等待队列的第一个节点，
         * *  是的话，则尝试获取锁（不判断等待队列是否有节点，直接抢锁）。
         * *  不是的话，判断是否过期，未过期则更新前继节点的waitStatus为signal，更新成功后，如果剩余时间大于1000ns，parkNanos当前线程。
         * *  被中断后抛出中断异常。
         *
         */
        try {
            boolean isLocked = lock.tryLock(1000, TimeUnit.SECONDS);
            if (isLocked) {
                try {
                    // 处理任务
                } catch (Exception ex) {

                } finally {
                    lock.unlock(); // 释放锁
                }
            } else {
                // 如果不能获取锁，则直接做其他事情
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
