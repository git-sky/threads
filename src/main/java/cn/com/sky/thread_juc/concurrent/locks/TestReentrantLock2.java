package cn.com.sky.thread_juc.concurrent.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock2 extends Thread {

    InnerLock lock;
    private int id;

    public TestReentrantLock2(int i, InnerLock lock) {
        this.id = i;
        this.lock = lock;
    }

    public void run() {
        lock.print(id);
    }

    public static void main(String args[]) {
        ExecutorService service = Executors.newCachedThreadPool();
        InnerLock lock = new InnerLock();
        for (int i = 0; i < 10; i++) {
            service.submit(new TestReentrantLock2(i, lock));
        }
        service.shutdown();
    }
}

class InnerLock {
    private ReentrantLock lock = new ReentrantLock();

    public void print(int str) {
        try {
            lock.lock();
            System.out.println(str + " get lock ");
            Thread.sleep((int) (Math.random() * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(str + " release lock ");
            lock.unlock();
        }
    }
}