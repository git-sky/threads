package cn.com.sky.thread_classic.producer_consumer.demo2;

/**
 * <pre>
 *
 * notify方法很容易引起死锁。
 * notifyAll方法则是线程的安全唤醒方法。
 *
 * wait()线程是被放入对象的等待池中，等待池的线程只有通过notify、notifyAll或者interrupt才能进入锁池中，
 * 而锁池的线程只有拿到锁标识后才进入runnable状态等待cpu时间片。
 *
 * 等待池-->锁池-->拿到锁--->抢占cpu
 */
public class TestProducerConsumer2 {

    public static void main(String[] args) {

        Goods goods = new Goods();

        int produceThreads = (int) (Math.random() * 1000);
        int consumeThreads = (int) (Math.random() * 100);
        System.out.println("produceThreads=" + produceThreads);
        System.out.println("consumeThreads=" + consumeThreads);

        for (int i = 0; i < produceThreads; i++) {
            new Thread(new Producer(goods, consumeThreads)).start();
        }
        for (int i = 0; i < consumeThreads; i++) {
            new Thread(new Consumer(goods, produceThreads)).start();

        }
    }
}

class Producer implements Runnable {

    private Goods goods;
    private int num;

    Producer(Goods goods, int num) {
        this.goods = goods;
        this.num = num;
    }

    @Override
    public void run() {
        for (int i = 0; i < num; i++) {
            System.out.println("Producer before produce...." + i);
            goods.produce(1);
            System.out.println("Producer after produce...." + i);

        }
    }

}

class Consumer implements Runnable {

    private Goods goods;
    private int num;

    Consumer(Goods goods, int num) {
        this.goods = goods;
        this.num = num;
    }

    @Override
    public void run() {
        for (int i = 0; i < num; i++) {
            System.out.println("Consumer before consume...." + i);
            goods.consume(1);
            System.out.println("Consumer after consume...." + i);
        }
    }
}

class Goods {

    int totalSpace = 10;// 当前空间
    int usedSpace = 0;// 已用空间

    public synchronized void produce(int num) {

        while (usedSpace + num > totalSpace) {
            try {
                wait();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        usedSpace += num;
        notifyAll();// 安全唤醒方法
        // notify();//容易导致死锁

    }

    public synchronized void consume(int num) {

        while (usedSpace - num < 0) {
            try {
                wait();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        usedSpace -= num;
        notifyAll();// 安全唤醒方法
        // notify();//容易导致死锁
    }
}
