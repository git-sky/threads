package cn.com.sky.threads.concurrent.spinlock.spin;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 */
public class spin {

    private AtomicReference atomicReference = new AtomicReference();

    public void lock() {

        System.out.println(atomicReference.get());

        Thread thread = Thread.currentThread();

        while (!atomicReference.compareAndSet(null, thread)) {

        }

    }

    public void unlock() {

        Thread thread = Thread.currentThread();

        atomicReference.compareAndSet(thread, null);

    }
}
