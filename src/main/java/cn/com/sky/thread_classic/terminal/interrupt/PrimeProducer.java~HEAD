package cn.com.sky.thread_classic.terminal.interrupt;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * 5. Interrupts can be swallowed if you know the thread is about to exit.
 */
public class PrimeProducer extends Thread {

    private final BlockingQueue<BigInteger> queue;


    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed) {
            /* Allow thread to exit */
        }
    }


    public void cancel() {
        interrupt();
    }
}