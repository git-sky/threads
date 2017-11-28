package cn.com.sky.threads.thread.terminal.interrupt;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 3. Restoring the interrupted status after catching InterruptedException
 */
public class TaskRunner implements Runnable {
    private BlockingQueue<Task> queue;

    public TaskRunner(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                Task task = queue.poll(10, TimeUnit.SECONDS);
                task.execute();
            }
        }
        catch (InterruptedException e) {
            // Restore the interrupted status
            Thread.currentThread().interrupt();
        }
    }
}