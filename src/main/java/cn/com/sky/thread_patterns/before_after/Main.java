package cn.com.sky.thread_patterns.before_after;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * before after模式。
 */
public class Main {

    public static void main(String[] args) {

        Lock lock = new ReentrantLock();

        lock.lock();//before
        try {
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//after
        }

        System.out.println("finish...");
    }

    public static void execute() {
        System.out.println("execute....");
    }
}