package cn.com.sky.threads.concurrent.atomic.basic;

import org.junit.Test;

import java.util.concurrent.atomic.DoubleAdder;


public class TestDoubleAdder {

    @Test
    public void test() {
        DoubleAdder doubleAdder = new DoubleAdder();
        doubleAdder.add(12);
    }
}