package cn.com.sky.thread_juc.concurrent.locks.park;

import java.util.concurrent.locks.LockSupport;

/**
 * <pre>
 *
 * LockSupport是用来创建锁和其他同步类的基本线程阻塞原语。
 *
 * LockSupport中的park()和 unpark()的作用分别是阻塞线程和解除阻塞线程，而且park()和unpark()不会遇到“Thread.suspend 和 Thread.resume所可能引发的死锁”问题。
 * 因为park()和 unpark()有许可的存在；调用 park() 的线程和另一个试图将其 unpark() 的线程之间的竞争将保持活性。
 *
 * LockSupport是通过调用Unsafe函数中的接口实现阻塞和解除阻塞的。
 *
 * </pre>
 */
public class TestLockSupport2 {

    private static Thread mainThread;

    public static void main(String[] args) {

        ThreadA ta = new ThreadA("ta");
        // 获取主线程
        mainThread = Thread.currentThread();

        System.out.println(Thread.currentThread().getName() + " start ta");
        ta.start();

        System.out.println(Thread.currentThread().getName() + " block");

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}

        // 主线程阻塞
        LockSupport.park(mainThread);

        System.out.println(Thread.currentThread().getName() + " continue");
    }

    static class ThreadA extends Thread {

        public ThreadA(String name) {
            super(name);
        }

        public void run() {
            System.out.println(Thread.currentThread().getName() + " wakup others");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 唤醒“主线程”
            LockSupport.unpark(mainThread);
        }
    }
}