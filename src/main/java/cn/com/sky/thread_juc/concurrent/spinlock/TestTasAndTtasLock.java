package cn.com.sky.thread_juc.concurrent.spinlock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 *
 * http://xw-z1985.iteye.com/blog/2055552
 *
 */
public class TestTasAndTtasLock {
    // 并发线程数
    private final static int NTHREAD = 1;
    // 计数
    private static volatile int count = 0;
    // 最大计数
    private final static int MAX = 100000;
    // TAS锁
    // private static TasLock lock = new TasLock();
    // TTAS锁
    private static TtasLock lock = new TtasLock();

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch endGate = new CountDownLatch(1);
        final CountDownLatch startGate = new CountDownLatch(1);
        ExecutorService e = Executors.newFixedThreadPool(NTHREAD);
        for (int i = 0; i < NTHREAD; i++) {
            e.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        while (true) {
                            lock.lock();
                            if (count < MAX) {
                                count++;
                                lock.unlock();
                                continue;
                            }
                            lock.unlock();
                            break;
                        }
                        endGate.countDown();
                    } catch (InterruptedException ignored) {
                    }

                }
            });
        }
        long start = System.currentTimeMillis();
        startGate.countDown();
        endGate.await();
        long end = System.currentTimeMillis();
        long time = end - start;
        e.shutdown();
        System.out.print(time);
    }
}