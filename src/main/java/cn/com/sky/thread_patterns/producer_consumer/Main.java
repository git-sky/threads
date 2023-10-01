package cn.com.sky.thread_patterns.producer_consumer;


/**
 * Producer-Consumer 模式
 * <pre>
 *
 *     适用的情况
 * 由多个线程之间处理生产消费的关系, 并且生产和消费不是即时处理的情况, 其中涉及到数据量的线程安全性问题.
 *
 * 实现的方式
 * 在Producer和Consumer之间设立一个中转站Channel, 让Channel来保存和维护数据的安全, 这样生产者和消费者之间就解耦了, 与他们有关的对象是Channel, 并且Channel是线程安全的.
 *
 * 相关的模式
 * Channel角色保证数据安全状态的时候可以使用Guarded Suspension模式.
 * 在Future模式中, 传递返回值的时候, 可以使用Producer-Consumer模式.
 * Worker-Thread模式中, 对于Worker的请求可以使用Producer-Consumer模式对请求进行控制.
 * 代码
 *
 *
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        Table table = new Table(3);
        new MakerThread("maker-1", table, 1000).start();
        new MakerThread("maker-2", table, 1000).start();
        new MakerThread("maker-3", table, 1000).start();
        new ConsumerThread("consumer-1", table, 1).start();
        new ConsumerThread("consumer-2", table, 2).start();
        new ConsumerThread("consumer-3", table, 3).start();
    }
}