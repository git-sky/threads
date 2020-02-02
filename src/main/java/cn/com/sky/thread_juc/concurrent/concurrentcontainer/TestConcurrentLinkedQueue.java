package cn.com.sky.thread_juc.concurrent.concurrentcontainer;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 在并发编程中我们有时候需要使用线程安全的队列。
 * 如果我们要实现一个线程安全的队列有两种实现方式一种是使用阻塞算法，另一种是使用非阻塞算法。
 * 使用阻塞算法的队列可以用一个锁（入队和出队用同一把锁）或两个锁（入队和出队用不同的锁）等方式来实现，而非阻塞的实现方式则可以使用循环CAS的方式来实现。
 * ConcurrentLinkedQueue使用非阻塞算法实现。
 *
 * </pre>
 */
public class TestConcurrentLinkedQueue {

    private static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();
    private static int count = 2; // 线程个数

    // CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
    private static CountDownLatch latch = new CountDownLatch(count);

    public static void main(String[] args) throws InterruptedException {
        long timeStart = System.currentTimeMillis();
        ExecutorService es = Executors.newFixedThreadPool(4);
        TestConcurrentLinkedQueue.offer();
        for (int i = 0; i < count; i++) {
            es.submit(new Poll());
        }
        latch.await(); // 使得主线程(main)阻塞直到latch.countDown()为零才继续执行
        System.out.println("cost time " + (System.currentTimeMillis() - timeStart) + " ms");
        es.shutdown();
    }

    /**
     * 生产
     */
    public static void offer() {
        for (int i = 0; i < 10; i++) {
            System.out.println("offer:" + i);
            queue.offer(i);

//            queue.add(i);
        }

    }

    /**
     * 消费
     */
    static class Poll implements Runnable {
        public void run() {
            // while (queue.size()>0) {
            while (!queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + ",poll:" + queue.poll());
            }
            latch.countDown();
        }
    }
}