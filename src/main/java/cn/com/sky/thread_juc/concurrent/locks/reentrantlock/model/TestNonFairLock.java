package cn.com.sky.thread_juc.concurrent.locks.reentrantlock.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestNonFairLock {

    public static void main(String[] args) {
        Lock nonFairLock = new ReentrantLock(false);


        /**
         * 非公平锁逻辑：
         * 先抢锁=》如果抢锁失败，判断是否有人占用锁，无人占用则再次抢锁，
         * 如果再次抢锁失败 => 则创建节点并放到队尾
         *  => 循环判断：
         *  本节点是否为等待队列的第一个节点，
         *  是的话，则尝试获取锁（不判断等待队列是否有节点，直接抢锁）。
         *  不是的话，则更新前继节点的waitStatus为signal，更新成功后，park当前线程。
         */
        nonFairLock.lock();
        try {
            // to do something
        } catch (Exception ex) {

        } finally {
            nonFairLock.unlock(); // 释放锁
        }
    }
}
