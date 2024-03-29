package cn.com.sky.thread_patterns.worker_thread;

import java.util.Random;

/**
 * 任务角色。
 */
public class Request {
    private final String name; //  委托者
    private final int number;  // 请求编号
    private static final Random random = new Random();

    public Request(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public void execute() {
        System.out.println(Thread.currentThread().getName() + " executes " + this);
        System.out.println("i am a request!");
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
        }
    }

    @Override
    public String toString() {
        return "[ Request from " + name + " No." + number + " ]";
    }
}