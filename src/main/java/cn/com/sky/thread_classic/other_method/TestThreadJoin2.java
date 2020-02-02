package cn.com.sky.thread_classic.other_method;

import org.junit.Test;

/**
 * <pre>
 *
 * join() 方法主要是让调用这个方法的thread完成run方法里面的东西后， 再执行join()方法后面的代码。
 * 父线程会被wait阻塞。当子线程对象t退出后，会在native方法中调用线程对象的natifyAll方法，然后main线程被唤醒继续执行。
 *
 * 原理：
 * 其实Join方法实现是通过wait（小提示：Object 提供的方法）进行线程控制的。
 * 当main线程调用t.join时候，main线程会获得线程对象t的锁（wait 意味着拿到该对象的锁),调用该对象的wait(等待时间)，
 * 直到该对象唤醒main线程，比如退出后，线程对象t退出后，会在native方法中调用线程对象的natifyAll方法，然后执行main线程的后续部分代码。
 */
public class TestThreadJoin2 {

    @Test
    public void test() {
        System.out.println("main...............开始");
        Thread t1 = new Thread1("t1");
        Thread t2 = new Thread2("t2", t1);
        t1.start();
        t2.start();
        try {
            System.out.println("main waiting......");
            t1.join();// main等待t1执行完成，才能执行。（本质上main线程阻塞在子线程对象上了）
            System.out.println("main has been waked up..........");
            // t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main...............结束");

    }


    class Thread1 extends Thread {

        public Thread1(String name) {
            super(name);
        }

        public void run() {
            for (int i = 0; i <= 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": i=" + i);
            }

        }
    }

    class Thread2 extends Thread {

        Thread thread;

        public Thread2(String name) {
            super(name);
        }

        public Thread2(String name, Thread t1) {
            super(name);
            this.thread = t1;
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " is waiting..........");
            try {
                Thread.sleep(100);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " has been waked up............");
        }
    }

}

