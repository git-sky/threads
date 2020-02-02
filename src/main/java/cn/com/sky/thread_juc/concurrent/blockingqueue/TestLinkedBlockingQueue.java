package cn.com.sky.thread_juc.concurrent.blockingqueue;

import org.junit.Test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>
 *
 * 阻塞队列
 * 有头指针和尾指针的单链表结构
 *
 * 原理：
 * 有头指针和尾指针的单链表实现， 有两把锁和条件变量：
 * 头部一把锁，一个条件变量，控制获取元素线程之间的互斥。
 * 尾部一把锁，一个条件变量，控制放入元素线程之间的互斥。
 *
 * 描述：
 *  有头指针和尾指针的单链表实现，有两把锁和条件变量：
 *  头部一把锁，一个条件变量，控制获取元素线程之间的互斥。
 *  尾部一把锁，一个条件变量，控制放入元素线程之间的互斥。
 *
 * 当队列满的时候，消费者消费并通知生产者生产，队列空的时候，生产者生产并通知消费者消费。只有这个时机才会生产者和消费者有交互。其他情况生产者和消费者互不影响。
 *
 * 生产者生产之后，如果队列不满，则通知其他生产者进行生产。在队列从空变成有的时候，则通知消费者消费。
 * 消费者消费之后，如果队列不为空，则通知其他消费者进行消费。在队列从满开始消费的时候，通知生产者生产。
 *
 * </pre>
 */
public class TestLinkedBlockingQueue {

    @Test
    public void test() {

        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        for (int i = 0; i < 30; i++) {
            queue.add(i);
            try {
                queue.put(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            queue.offer(i);
        }

        queue.iterator();
    }

}
