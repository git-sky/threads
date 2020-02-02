package cn.com.sky.thread_patterns.worker_thread;

import java.util.Random;

/**
 * 委托者角色，产生任务，并发送到通道。
 */
public class ClientThread extends Thread {

    private final Channel channel;
    private static final Random random = new Random();

    public ClientThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 100; i++) {
                Request request = new Request(getName(), i);
                channel.putRequest(request);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
        }
    }
}