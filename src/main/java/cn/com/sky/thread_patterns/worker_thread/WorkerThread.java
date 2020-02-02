package cn.com.sky.thread_patterns.worker_thread;

/**
 * 工作者角色，从通道获取任务，并执行。
 */
public class WorkerThread extends Thread {
    private final Channel channel;

    public WorkerThread(String name, Channel channel) {
        super(name);
        this.channel = channel;
    }

    @Override
    public void run() {
        while (true) {
            Request request = channel.takeRequest();
            request.execute();
        }
    }
}