package cn.com.sky.thread_juc.concurrent.locks.reentrantlock.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestFairLock {

    public static void main(String[] args) {
        Lock fairLock = new ReentrantLock(true);


        /**
         * 公平锁逻辑：
         * 判断等待队列是否有其他节点在等待 => 如有，则创建节点并放到队尾
         *  => 循环判断：
         *  本节点是否为等待队列的第一个节点，
         *  是的话，则尝试获取锁（等待队列有节点则获取失败）。
         *  不是的话，则更新前继节点的waitStatus为signal，更新成功后，park当前线程。
         */
        fairLock.lock();
        try {
            // to do something
        } catch (Exception ex) {

        } finally {
            fairLock.unlock(); // 释放锁
        }
    }
}
