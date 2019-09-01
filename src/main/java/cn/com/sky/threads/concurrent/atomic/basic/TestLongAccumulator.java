package cn.com.sky.threads.concurrent.atomic.basic;


import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

/**
 * <pre>
 *
 *  LongAccumulator是LongAdder的功能增强版。LongAdder的API只有对数值的加减，而LongAccumulator提供了自定义的函数操作。
 *
 *
 * </pre>
 */
public class TestLongAccumulator {


    // 找出最大值
    public static void main(String[] args) throws InterruptedException {

        LongAccumulator accumulator = new LongAccumulator(Long::max, Long.MIN_VALUE);
        Thread[] ts = new Thread[10];

        for (int i = 0; i < 10; i++) {
            ts[i] = new Thread(() -> {
                Random random = new Random();
                long value = random.nextLong();
                System.out.println(value);
                accumulator.accumulate(value); // 比较value和上一次的比较值，然后存储较大者
            });
            ts[i].start();
        }
        for (int i = 0; i < 10; i++) {
            ts[i].join();
        }
        System.out.println(accumulator.longValue());
    }


}