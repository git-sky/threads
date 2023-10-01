package cn.com.sky.thread_juc.concurrent.blockingqueue;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * <pre>
 *
 * Java 6的并发编程包中的SynchronousQueue是一个没有数据缓冲的BlockingQueue，生产者线程对其的插入操作put必须等待消费者的移除操作take，反过来也一样。
 *
 * 不像ArrayBlockingQueue或LinkedBlockingQueue，SynchronousQueue内部并没有数据缓存空间。
 * 你不能调用peek()方法来看队列中是否有数据元素 ，因为数据元素只有当你试着取走的时候才可能存在，不取走而只想偷窥一下是不行的，当然遍历这个队列的操作也是不允许的。
 * 队列头元素是第一个排队要插入数据的线程，而不是要交换的数 。
 * 数据是在配对的生产者和消费者线程之间直接传递的，并不会将数据缓冲数据到队列中。
 * 可以这样来理解：生产者和消费者互相等待对方，握手，然后一起离开。
 *
 * SynchronousQueue的一个使用场景是在线程池里。
 *
 * Executors.newCachedThreadPool()就使用了SynchronousQueue，这个线程池根据需要新任务到来时创建新的线程，如果有空闲线程则会重复使用，线程空闲了60秒后会被回收。
 */
public class TestSynchronousQueue {

    @Test
    public void test() {

        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(1);
        Executors.newSingleThreadExecutor();

        boolean fair = true;
        SynchronousQueue<Object> sq = new SynchronousQueue<>(fair);

        try {
            sq.put(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            sq.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
