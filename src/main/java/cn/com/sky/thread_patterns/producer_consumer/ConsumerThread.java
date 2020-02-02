package cn.com.sky.thread_patterns.producer_consumer;

import java.util.Random;


public class ConsumerThread extends Thread {
    private final Table table;
    private final Random random;

    public ConsumerThread(String s, Table table, long seed) {
        super(s);
        this.table = table;
        this.random = new Random(seed);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(random.nextInt(1000));
                String cake = table.take();
                System.out.println(cake + " is eaten by " + getName());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}