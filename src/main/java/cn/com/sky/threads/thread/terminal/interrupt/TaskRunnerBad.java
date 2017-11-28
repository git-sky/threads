package cn.com.sky.threads.thread.terminal.interrupt;

import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 4. Swallowing an interrupt -- don't do this
 */
public class TaskRunnerBad implements Runnable {
    private BlockingQueue<Task> queue;

    public TaskRunnerBad(BlockingQueue<Task> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (true) {
                Task task = queue.poll(10L, SECONDS);
                task.execute();
            }
        }
        catch (InterruptedException swallowed) {
             /* DON'T DO THIS - RESTORE THE INTERRUPTED STATUS INSTEAD */
        }
    }
}