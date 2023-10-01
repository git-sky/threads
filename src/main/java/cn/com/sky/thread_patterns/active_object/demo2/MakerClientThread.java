package cn.com.sky.thread_patterns.active_object.demo2;

import java.util.concurrent.Future;

public class MakerClientThread extends Thread {
    private final ActiveObject activeObject;
    private final char fillChar;

    public MakerClientThread(String s, ActiveObject activeObject) {
        super(s);
        this.activeObject = activeObject;
        this.fillChar = s.charAt(0);
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                Future<String> future = activeObject.makeString(i, fillChar);
                Thread.sleep(10);
                String value = future.get();
                System.out.println(Thread.currentThread().getName() + ": value = " + value);
            }
        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + ":" + e);
        }
    }
}