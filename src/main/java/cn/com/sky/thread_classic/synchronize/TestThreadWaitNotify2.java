package cn.com.sky.thread_classic.synchronize;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * 每个对象实例都有一个等待线程队列。 这些线程都是等待对该对象的同步方法的调用许可。
 *
 *
 *
 * 所谓互斥，就是不同线程通过竞争进入临界区（共享的数据和硬件资源），为了防止访问冲突，在有限的时间内只允许其中之一独占性的使用共享资源。如不允许同时写
 *
 * 同步关系则是多个线程彼此合作，通过一定的逻辑关系来共同完成一个任务。一般来说，同步关系中往往包含互斥，同时对临界区的资源会按照某种逻辑顺序进行访问。如先生产后使用
 *
 * 总的来说，两者的区别就是：
 * 互斥是通过竞争对资源的独占使用，彼此之间不需要知道对方的存在，执行顺序是一个乱序。
 * 同步是协调多个相互关联线程合作完成任务，彼此之间知道对方存在，执行顺序往往是有序的。
 *
 *
 * synchronized实现线程互斥。
 * wait与notify/notifyAll实现线程同步。
 *
 *
 * wait()、wait(0)、wait(0,0)
 * wait(times) （注意：times>0）
 * 1.线程进入“休眠”，等待times时间后自动唤醒或者等待notify/notifyAll唤醒。如果times时间不到，notify/notifyAll也可以立即唤醒该线程。
 * 2.立即释放对象上的锁，进入条件队列。
 * 3.线程被唤醒后，抢占锁，如果得到锁，从休眠处继续执行。如果得不到锁，继续等待，但是不会再次"休眠"。
 *
 *
 * Java语言规范中的解释：
 * 1.释放锁
 * 2.进入等待集（wait set）
 * 3.只有线程移除等待集才会继续执行。
 * 移除等待集的触发条件：
 * a> notify/notifyAll
 * b> 等待时间到
 * c> 中断
 * d> 伪唤醒
 */
public class TestThreadWaitNotify2 {

    public static void main(String[] args) {

        final Object obj = new Object();

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (obj) {
                        try {
                            System.out.println("before wait:" + Thread.currentThread().getName() + ":" + System.currentTimeMillis());

                            // 1.当前线程“休眠”，等待被唤醒；2.释放obj对象上的锁。
                            // obj.wait(0);
                            obj.wait(0, 0);

                            System.out.println("after wait:" + Thread.currentThread().getName() + ":" + System.currentTimeMillis());
                        } catch (InterruptedException e) {
//                            e.printStackTrace();
                            System.out.println(e.getMessage());
                        }
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //
        // notify()方法能够唤醒一个正在等待该对象的monitor的线程。具体唤醒哪个线程是随机的。从前面唤醒。
        // nofityAll()方法能够唤醒所有正在等待该对象的monitor的线程。从后向前唤醒。
        synchronized (obj) {
            System.out.println("before nofity..............");
            obj.notify();// 只能随机唤醒一个线程，唤醒的线程执行完后，没有唤醒的都会继续阻塞。 所以程序不会结束。
//            obj.notifyAll();// 唤醒所有的线程，所有的线程都会执行完毕。所以程序会结束。
            System.out.println("after notify..........");


            long start = System.currentTimeMillis();

            for (Thread t : threadList) {
                t.interrupt();//中断其他线程，并不会立即抛出中断异常，本synchronized方法结束后，被中断的线程才会抛出中断异常。
            }

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println((System.currentTimeMillis() - start) + "ms");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
