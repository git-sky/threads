package cn.com.sky.thread_juc.concurrent.atomic.basic;

import java.util.concurrent.atomic.AtomicLong;

public class TestAtomicLong {


    public static void main(String[] args) {
        AtomicLong al = new AtomicLong();
        al.addAndGet(1);

    }

}
