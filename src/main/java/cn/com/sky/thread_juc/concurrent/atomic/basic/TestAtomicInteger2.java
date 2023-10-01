package cn.com.sky.thread_juc.concurrent.atomic.basic;

import java.util.concurrent.atomic.AtomicInteger;

public class TestAtomicInteger2 {

    AtomicInteger counter = new AtomicInteger(0);

    public int count() {
        int result;
        boolean flag;
        do {
            result = counter.get();
            // 断点
            // 单线程下, compareAndSet返回永远为true,
            // 多线程下, 在与result进行compare时, counter可能被其他线程set了新值, 这时需要重新再取一遍再比较,
            // 如果还是没有拿到最新的值, 则一直循环下去, 直到拿到最新的那个值
            flag = counter.compareAndSet(result, result + 1);
        } while (!flag);

        return result;
    }

    public static void main(String[] args) {

        final TestAtomicInteger2 c = new TestAtomicInteger2();

        new Thread(() -> c.count()).start();

        new Thread(() -> c.count()).start();

        new Thread(() -> c.count()).start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(c.counter);
    }
}
