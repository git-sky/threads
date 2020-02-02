package cn.com.sky.thread_juc.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 *
 * 生产者/消费者模式（Lock+Condition）
 * </pre>
 */
public class TestProducerConsumer {
    public static void main(String[] args) {

        Godown godown = new Godown(10);

        Consumer c1 = new Consumer(10, godown);
        // Consumer c2 = new Consumer(20, godown);
        // Consumer c3 = new Consumer(30, godown);

        Producer p1 = new Producer(5, godown);
        // Producer p2 = new Producer(10, godown);
        // Producer p3 = new Producer(10, godown);
        // Producer p4 = new Producer(10, godown);
        // Producer p5 = new Producer(10, godown);
        // Producer p6 = new Producer(10, godown);
        // Producer p7 = new Producer(80, godown);

        c1.start();
        // c2.start();
        // c3.start();

        p1.start();
        // p2.start();
        // p3.start();
        // p4.start();
        // p5.start();
        // p6.start();
        // p7.start();

    }
}

/**
 * 仓库
 */
class Godown {
    public static final int max_size = 10; // 最大库存量
    public int curnum; // 当前库存量

    Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();

    Godown() {
    }

    Godown(int curnum) {
        this.curnum = curnum;
    }

    /**
     * 生产指定数量的产品
     */
    public void produce(int neednum) {
        lock.lock();
        try {
            // 测试是否需要生产
            while (neednum + curnum > max_size) {
                System.out.println("要生产的产品数量" + neednum + "超过剩余库存量" + (max_size - curnum) + "，暂时不能执行生产任务!");
                try {
                    System.out.println("生产者线程-----当前线程名称：" + Thread.currentThread().getName());
                    // 当前的生产线程等待,释放Godown的当前对象锁
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 满足生产条件，则进行生产，这里简单的更改当前库存量
            curnum += neednum;
            System.out.println("已经生产了" + neednum + "个产品，现仓储量为" + curnum);

            condition.signalAll();

        } finally {
            lock.unlock();
        }
    }

    /**
     * 消费指定数量的产品
     */
    public synchronized void consume(int neednum) {
        lock.lock();
        try {
            // 测试是否可消费
            while (curnum < neednum) {
                try {
                    System.out.println("before wait 消费者线程-----当前线程名称：" + Thread.currentThread().getName());
                    // 当前的消费线程等待，释放Godown的当前对象锁
                    condition.await();
                    System.out.println("after wait 消费者线程-----当前线程名称：" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 满足消费条件，则进行消费，这里简单的更改当前库存量
            curnum -= neednum;
            System.out.println("已经消费了" + neednum + "个产品，现仓储量为" + curnum);

            condition.signalAll();

        } finally {
            lock.unlock();
        }
    }
}

/**
 * 生产者
 */
class Producer extends Thread {
    private int neednum; // 生产产品的数量
    private Godown godown; // 仓库

    Producer(int neednum, Godown godown) {
        this.neednum = neednum;
        this.godown = godown;
    }

    public void run() {
        while (true) {
            // 生产指定数量的产品
            godown.produce(neednum);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 消费者
 */
class Consumer extends Thread {
    private int neednum; // 消费产品的数量
    private Godown godown; // 仓库

    Consumer(int neednum, Godown godown) {
        this.neednum = neednum;
        this.godown = godown;
    }

    public void run() {
        while (true) {
            // 消费指定数量的产品
            godown.consume(neednum);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
