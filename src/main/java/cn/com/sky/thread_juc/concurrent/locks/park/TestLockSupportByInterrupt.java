package cn.com.sky.thread_juc.concurrent.locks.park;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport的park和Object的wait一样也能响应中断。
 * 但是park它不会抛出InterruptedException。
 */
public class TestLockSupportByInterrupt {

    public static void main(String[] args) throws InterruptedException {

        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                LockSupport.park();
                System.out.println(Thread.currentThread() + " awake!");

                //park可以响应中断，中断park后, 不会清空中断状态，也不会抛出InterruptedException。
                System.out.println("中断状态1：" + Thread.currentThread().isInterrupted());
            }
        });
        t.setName("t1");
        t.start();
        Thread.sleep(3000);

        // 2. 中断
        t.interrupt();
        System.out.println("中断状态2：" +t.isInterrupted());
    }
}