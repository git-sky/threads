package cn.com.sky.thread_patterns.producer_consumer;


/**
 * @author youngxinler  19-6-1 上午11:42
 * @version 0.1
 * <p>
 * 这里的table其实相当于一个池子，存放着生产者生产的物品，等待消费者来消费。
 * 为什么用table来保证线程安全？
 * 1.明白要保护的变量, 这个例子中会造成线程不安全的变量是buffer[],而buffer位于table.
 * 2.与maker和consumer"断绝关系", 保证了table的线程安全, 那么对于maker和consumer就可以大胆放心的写了.
 * 3.保证了table的通用性.
 **/

public class Table {
    private final String[] buffer;
    private int tail;
    private int head;
    private int count;

    public Table(int count) {
        this.buffer = new String[3];
        this.tail = 0;
        this.head = 0;
        this.count = 0;
    }

    public synchronized void put(String cake) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + " puts " + cake);
        while (count >= buffer.length) {
            wait();
        }
        buffer[tail] = cake;
        tail = (tail + 1) % buffer.length;
        count++;
        notifyAll();
    }

    public synchronized String take() throws InterruptedException {
        while (count <= 0) {
            wait();
        }
        String cake = buffer[head];
        head = (head + 1) % buffer.length;
        count--;
        System.out.println(Thread.currentThread().getName() + " take " + cake);
        notifyAll();
        return cake;
    }
}