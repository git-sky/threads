package cn.com.sky.thread_classic.thread_local.random_demo;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <pre>
 * 问题：ThreadLocalRandom多线程下产生相同随机数。
 *
 * 多线程共享同一个ThreadLocalRandom.current()，会产生相同的随机数。
 * 每个线程使用不同的ThreadLocalRandom.current()，才可以产生不相同的随机数。
 *
 * 所以，位置1有问题，位置2是正确的。
 *
 *
 * </pre>
 */
public class ThreadLocalRandom3 {

    //位置1
//    private static final ThreadLocalRandom RANDOM =
//            ThreadLocalRandom.current();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Player().start();
        }
    }

    private static class Player extends Thread {
        @Override
        public void run() {
//            System.out.println(getName() + ": " + RANDOM.nextInt(100));

            //位置2
            System.out.println(getName() + ": " + ThreadLocalRandom.current().nextInt(100));

        }
    }
}