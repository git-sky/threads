package cn.com.sky.thread_juc.concurrent.spinlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestSpinLock {

    public static void main(String[] args) {

        // SpinLockable lock = new SpinLock();//不可重入锁
        // SpinLockable lock = new SpinLockReentrant();//可重入锁

        // SpinLockable lock = new TicketLock();//不可重入锁

        // SpinLockable lock = new CLHLock();//不可重入锁

        // SpinLockable lock = new MCSLock();//不可重入锁

        // SpinLockable lock = new BlockLock();//不可重入锁

        // SpinLockable lock = new CLHLock3();

//		SpinLockable lock = new TasLock();

        SpinLockable lock = new TtasLock();

        ExecutorService threadPool = Executors.newCachedThreadPool();

        threadPool.submit(new MyThread(lock));
        threadPool.submit(new MyThread(lock));
        threadPool.submit(new MyThread(lock));
        threadPool.submit(new MyThread(lock));

        threadPool.shutdown();

    }

}

class MyThread implements Runnable {

    private SpinLockable lock;

    public MyThread(SpinLockable lock) {
        this.lock = lock;
    }

    @Override
    public void run() {

        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " begin run ...");
            Thread.sleep(2000);
            // get();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " after run ...");
            lock.unlock();
        }

    }

    public void get() {

        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " begin get ...");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " after get ...");
            lock.unlock();
        }
    }
}
