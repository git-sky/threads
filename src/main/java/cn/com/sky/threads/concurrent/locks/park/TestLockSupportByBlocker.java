package cn.com.sky.threads.concurrent.locks.park;

import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 *
 * park的是当前线程，unpark的是被阻塞的线程。
 */
public class TestLockSupportByBlocker {
    public static void main(String[] args) {

        Thread thread = Thread.currentThread();

        Thread t2 = new Thread(new ParkB(thread));
        t2.setName("t2");
        t2.start();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t1 = new Thread(new ParkA(thread, t2));
        t1.setName("t1");
        t1.start();


        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main over...");

    }
}

class ParkA implements Runnable {

    private Thread t;
    private Thread son;

    public ParkA() {
    }

    ParkA(Thread t, Thread son) {
        this.t = t;
        this.son = son;
    }

    @Override
    public void run() {
        System.out.println("run before...........");
//        LockSupport.unpark(t);
//        LockSupport.unpark(t);
//        LockSupport.unpark(t);
        System.out.println("blocker："+LockSupport.getBlocker(son));
        LockSupport.unpark(son);
        System.out.println("blocker："+LockSupport.getBlocker(son));

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LockSupport.unpark(son);
        LockSupport.unpark(son);

        System.out.println("run after...........");
    }

}

class ParkB implements Runnable {

    private Thread t;

    public ParkB() {
    }

    ParkB(Thread t) {
        this.t = t;
    }

    @Override
    public void run() {
        System.out.println("park2 run before...........");

//       parkBlocker是用于记录线程是被谁阻塞的。可以通过LockSupport的getBlocker获取到阻塞的对象。用于监控和分析线程用的。
        LockSupport.park(t);//阻塞当前线程，不是阻塞t线程。

        System.out.println("first i waked up...");

        LockSupport.park(t);

        System.out.println("second i waked up...");

        System.out.println("i am blocked...");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        System.out.println("park2 run after...........");
    }
}
