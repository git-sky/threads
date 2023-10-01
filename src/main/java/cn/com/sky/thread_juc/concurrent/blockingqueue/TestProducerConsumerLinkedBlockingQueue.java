package cn.com.sky.thread_juc.concurrent.blockingqueue;

import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <pre>
 *
 * 经典的方法是使用wait和notify方法在生产者和消费者线程中合作，在队列满了或者队列是空的条件下阻塞。
 * Java5的阻塞队列（BlockingQueue）数据结构更简单，
 * 因为它隐含的提供了这些控制，现在你不需要使用wait和nofity在生产者和消费者之间通信了，阻塞队列的put()方法将阻塞如果队列满了， 队列take()方法将阻塞如果队列是空的。
 *
 * 阻塞队列实现生产者消费者模式超级简单，它提供开箱即用支持阻塞的方法put()和take()，开发者不需要写困惑的wait-nofity代码去实现通信。
 *
 * BlockingQueue一个接口 ，Java5提供了不同的现实，如ArrayBlockingQueue和LinkedBlockingQueue，两者都是先进先出 （FIFO）顺序。
 *
 * 而ArrayLinkedQueue是自然有界的 ，LinkedBlockingQueue可选的边界。
 *
 * 下面这是一个完整的生产者消费者代码例子，对比传统的wait、nofity代码，它更易于理解。
 */

// 使用阻塞队列实现生产者消费者模式
public class TestProducerConsumerLinkedBlockingQueue {


    @Test
    public void test() {
        BlockingQueue<Integer> sharedQueue = new LinkedBlockingQueue<>();

        Thread producerThread = new Thread(new Producer(sharedQueue));
        Thread consumerThread = new Thread(new Consumer(sharedQueue));

        producerThread.start();
        consumerThread.start();
    }


    private class Producer implements Runnable {

        private final BlockingQueue<Integer> sharedQueue;

        public Producer(BlockingQueue<Integer> sharedQueue) {
            this.sharedQueue = sharedQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    System.out.println("Produced: " + i);
                    sharedQueue.put(i);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private class Consumer implements Runnable {

        private final BlockingQueue<Integer> sharedQueue;

        public Consumer(BlockingQueue<Integer> sharedQueue) {
            this.sharedQueue = sharedQueue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Consumed: " + sharedQueue.take());
                } catch (InterruptedException ex) {
                    Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
