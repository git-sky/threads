package cn.com.sky.thread_juc.concurrent.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *
 * 当使用一个条件变量时，signal唤醒的线程不确定，当一直唤醒一种角色时，就会有死锁的可能。notify始终是一个条件变量，所以有同样的问题，会有死锁的可能。
 * 当使用两个条件变量时，signal唤醒指定的等待集合，就不会导致死锁。
 */
public class TestNoDeadLockBySignal {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();
        Condition notEmpty = lock.newCondition();
        Condition notFull = lock.newCondition();

        Goods goods = new Goods(1, 0, lock, notEmpty, notFull);

        int threadNum = 2;
        int num = 2;

        int total = threadNum * num;

        for (int i = 0; i < threadNum; i++) {
            Producer p = new Producer(goods, 1, num);
            p.setName("P" + i);
            p.start();
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Consumer c = new Consumer(goods, 1, total);
        c.setName("C");
        c.start();

    }

}

class Producer extends Thread {

    Goods goods;
    int num;
    int runNum;

    Producer(Goods goods, int num, int runNum) {
        this.goods = goods;
        this.num = num;
        this.runNum = runNum;
    }

    public void run() {
        for (int i = 0; i < runNum; i++) {
            goods.produce(num);
        }
    }

}

class Consumer extends Thread {

    Goods goods;
    int num;
    int runNum;

    Consumer(Goods goods, int num, int runNum) {
        this.goods = goods;
        this.num = num;
        this.runNum = runNum;
    }

    public void run() {
        for (int i = 0; i < runNum; i++) {
            goods.consume(num);
        }
    }

}

class Goods {

    int totalSpace;
    int usedSpace;
    Lock lock;
    Condition notEmpty;
    Condition notFull;

    Goods(int totalSpace, int usedSpace, Lock lock, Condition notEmpty, Condition notFull) {
        this.totalSpace = totalSpace;
        this.usedSpace = usedSpace;
        this.lock = lock;
        this.notEmpty = notEmpty;
        this.notFull = notFull;
    }

    public void produce(int num) {

        lock.lock();

        System.out.println(Thread.currentThread().getName() + ": get lock .......");

        while (usedSpace + num > totalSpace) {
            try {
                System.out.println(Thread.currentThread().getName() + ": sleep and relase lock .......");
                notFull.await();
                System.out.println(Thread.currentThread().getName() + ": waked up and get lock  ....");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        usedSpace += num;
        System.out.println(Thread.currentThread().getName() + ": produce and relase lock and wake a thread .......");
        notEmpty.signal();
        lock.unlock();
    }

    public void consume(int num) {

        lock.lock();
        System.out.println(Thread.currentThread().getName() + ": get lock .......");

        while (usedSpace - num < 0) {
            try {
                System.out.println(Thread.currentThread().getName() + ": sleep and relase lock .......");
                notEmpty.await();
                System.out.println(Thread.currentThread().getName() + ": waked up and get lock .......");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        usedSpace -= num;
        System.out.println(Thread.currentThread().getName() + ": consume and relase lock and wake a thread .......");
        notFull.signal();
        lock.unlock();
    }

}
