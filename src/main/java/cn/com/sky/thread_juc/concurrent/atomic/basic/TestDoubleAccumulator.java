package cn.com.sky.thread_juc.concurrent.atomic.basic;


import org.junit.Test;

import java.util.concurrent.atomic.DoubleAccumulator;


public class TestDoubleAccumulator {

    @Test
    public void test() {
        DoubleAccumulator doubleAccumulator = new DoubleAccumulator(Double::max, Double.MIN_VALUE);
        doubleAccumulator.accumulate(10.0);
        doubleAccumulator.accumulate(5.0);
        doubleAccumulator.accumulate(15.0);
        doubleAccumulator.accumulate(8.0);

        System.out.println(doubleAccumulator.get());

    }
}