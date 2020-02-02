package cn.com.sky.thread_juc.concurrent.blockingqueue;

import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * <pre>
 *
 *  阻塞队列
 *
 *  ArrayBlockingQueue：
 *
 *  规定大小的BlockingQueue，其构造函数必须带一个int参数来指明其大小。其所含的对象是以FIFO（先入先出）顺序排序的。
 *
 * 实现原理：
 * 循环数组实现,有一把锁lock，两个条件变量notEmpty和notFull，控制数组中元素的获取和加入。
 * takeIndex,putIndex获取元素的索引和存放元素的索引。
 *
 * 描述：
 * 循环数组实现,有一把锁lock，两个条件变量notEmpty，notFull,控制数组中元素的获取和加入。takeIndex,putIndex获取元素的索引和存放元素的索引。
 * 因为只有一把锁，所以生产者与生产者，生产者与消费者，消费者与消费者都是互斥的。
 *
 * ArrayBlockingQueue性能高于LinkedBlockingQueue。
 *
 * ArrayBlockingQueue完全可以采用分离锁，从而实现生产者和消费者操作的完全并行运行。Doug Lea之所以没这样去做，
 * 也许是因为ArrayBlockingQueue的数据写入和获取操作已经足够轻巧，以至于引入独立的锁机制，除了给代码带来额外的复杂性外，其在性能上完全占不到任何便宜。
 *
 * </pre>
 */
public class TestArrayBlockingQueue {

    @Test
    public void test() {
        ArrayBlockingQueue<Integer> bqueue = new ArrayBlockingQueue<>(20);

        for (int i = 0; i < 30; i++) {
            // 将指定元素添加到此队列中，如果没有可用空间，将一直等待（如果有必要）。
            try {
                bqueue.put(i);//阻塞等待,可中断
                bqueue.take();//阻塞获取,可中断
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("put over ...");

            bqueue.add(i);
            System.out.println("add over ...");

            bqueue.offer(i);
            System.out.println("offer over ...");

            System.out.println("向阻塞队列中添加了元素:" + i);
        }
        System.out.println("over...");
    }
}