package cn.com.sky.thread_juc.concurrent.blockingqueue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *
 * 1. ArrayBlockingQueue
 *
 * 基于数组的阻塞队列实现，在ArrayBlockingQueue内部，维护了一个定长数组，以便缓存队列中的数据对象，这是一个常用的阻塞队列，除了一个定长数组外，
 * ArrayBlockingQueue内部还保存着两个整形变量， 分别标识着队列的头部和尾部在数组中的位置。
 * ArrayBlockingQueue在生产者放入数据和消费者获取数据，都是共用同一个锁对象 ，由此也意味着两者无法真正并行运行，这点尤其不同于LinkedBlockingQueue；
 * 按照实现原理来分析 ，ArrayBlockingQueue完全可以采用分离锁，从而实现生产者和消费者操作的完全并行运行。
 * Doug Lea之所以没这样去做，也许是因为ArrayBlockingQueue的数据写入和获取操作已经足够轻巧，以至于引入独立的锁机制，除了给代码带来额外的复杂性外，其在性能上完全占不到任何便宜。
 * ArrayBlockingQueue和LinkedBlockingQueue间还有一个明显的不同之处在于 ，前者在插入或删除元素时不会产生或销毁任何额外的对象实例，而后者则会生成一个额外的Node对象。
 * 这在长时间内需要高效并发地处理大批量数据的系统中 ，其对于GC的影响还是存在一定的区别。而在创建ArrayBlockingQueue时，我们还可以控制对象的内部锁是否采用公平锁，默认采用非公平锁。
 *
 * 2. LinkedBlockingQueue
 *
 * 基于链表的阻塞队列，同ArrayListBlockingQueue类似，其内部也维持着一个数据缓冲队列（该队列由一个链表构成），当生产者往队列中放入一个数据时，队列会从生产者手中获取数据，
 * 并缓存在队列内部，而生产者立即返回；
 * 只有当队列缓冲区达到最大值缓存容量时（LinkedBlockingQueue可以通过构造函数指定该值），才会阻塞生产者队列，直到消费者从队列中消费掉一份数据，
 * 生产者线程会被唤醒，反之对于消费者这端的处理也基于同样的原理 。而LinkedBlockingQueue之所以能够高效的处理并发数据，还因为其对于生产者端和消费者端分别采用了独立的锁来控制数据同步，
 * 这也意味着在高并发的情况下生产者和消费者可以并行地操作队列中的数据，以此来提高整个队列的并发性能。 作为开发者，我们需要注意的是，如果构造一个LinkedBlockingQueue对象
 * ，而没有指定其容量大小，LinkedBlockingQueue会默认一个类似无限大小的容量（Integer.MAX_VALUE），这样的话，如果生产者的速度一旦大于消费者的速度
 * ，也许还没有等到队列满阻塞产生，系统内存就有可能已被消耗殆尽了。
 *
 * ArrayBlockingQueue和LinkedBlockingQueue是两个最普通也是最常用的阻塞队列，一般情况下，在处理多线程间的生产者消费者问题，使用这两个类足以。
 *
 * </pre>
 */
public class TestProducerConsumerArrayBlockingQueue {

    @Test
    public void test() {
        // 建立一个装苹果的篮子
        final Basket basket = new Basket();

        // 定义苹果生产者
        class Producer implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        // 生产苹果
                        System.out.println("生产者准备生产苹果：" + System.currentTimeMillis());
                        basket.produce();
                        System.out.println("生产者生产苹果完毕：" + System.currentTimeMillis());
                        // 休眠300ms
                        Thread.sleep(300);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // 定义苹果消费者
        class Consumer implements Runnable {
            @Override
            public void run() {
                try {
                    while (true) {
                        // 消费苹果
                        System.out.println("消费者准备消费苹果：" + System.currentTimeMillis());
                        basket.consume();
                        System.out.println("消费者消费苹果完毕：" + System.currentTimeMillis());
                        // 休眠1000ms
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }

        ExecutorService service = Executors.newCachedThreadPool();
        Producer producer = new Producer();
        Consumer consumer = new Consumer();
        service.submit(producer);
        service.submit(consumer);
        // 程序运行5s后，所有任务停止
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        service.shutdownNow();
    }


    /**
     * 定义装苹果的篮子
     */
    private class Basket {
        // 篮子，能够容纳3个苹果
        BlockingQueue<String> basket = new ArrayBlockingQueue<>(3);

        // 生产苹果，放入篮子
        public void produce() throws InterruptedException {
            // put方法放入一个苹果，若basket满了，等到basket有位置
            basket.put("An apple");
        }

        // 消费苹果，从篮子中取走
        public String consume() throws InterruptedException {
            // get方法取出一个苹果，若basket为空，等到basket有苹果为止
            return basket.take();
        }
    }


}