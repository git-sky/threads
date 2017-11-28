package cn.com.sky.threads.thread.terminal.interrupt;

import java.util.concurrent.BlockingQueue;

/**
 * 6. Noncancelable task that restores interrupted status before returning
 */
public class NoncancelableTask {

    public Task getNextTask(BlockingQueue<Task> queue) {
        boolean interrupted = false;
        try {
            while (true) {
                try {
                    return queue.take();
                } catch (InterruptedException e) {
                    interrupted = true;
                    // fall through and retry
                }
            }
        } finally {
            if (interrupted)
                Thread.currentThread().interrupt();
        }
    }
}
