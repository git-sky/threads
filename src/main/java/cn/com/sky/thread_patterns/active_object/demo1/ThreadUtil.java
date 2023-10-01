package cn.com.sky.thread_patterns.active_object.demo1;


import java.util.Random;

public class ThreadUtil {

    public static void sleepRandom() {
        try {
            Thread.sleep(new Random().nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}