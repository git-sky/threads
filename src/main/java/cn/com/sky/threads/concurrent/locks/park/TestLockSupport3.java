package cn.com.sky.threads.concurrent.locks.park;

import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 *
 * 方法LockSupport.park阻塞当前线程除非/直到有个LockSupport.unpark方法被调用（unpark方法被提前调用也是可以的）。
 * unpark的调用是没有被计数的，因此在一个park调用前多次调用unpark方法只会解除一个park操作。
 */
public class TestLockSupport3 {
    public static void main(String[] args) {

        Thread thread = Thread.currentThread();

        Thread t2 = new Thread(new Park2(thread));
        t2.setName("t2");
        t2.start();

        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        Thread t1 = new Thread(new Park(thread));
//        t1.setName("t1");
//        t1.start();


        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main over...");

    }
}

class Park implements Runnable {

    private Thread t;

    public Park() {
    }

    Park(Thread t) {
        this.t = t;
    }

    @Override
    public void run() {
        System.out.println("run before...........");
        LockSupport.unpark(t);
        LockSupport.unpark(t);
        LockSupport.unpark(t);
        System.out.println("run after...........");
    }

}

class Park2 implements Runnable {

    private Thread t;

    public Park2() {
    }

    Park2(Thread t) {
        this.t = t;
    }

    @Override
    public void run() {
        System.out.println("park2 run before...........");

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
