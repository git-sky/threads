package cn.com.sky.threads.concurrent.spinlock.spin;

import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class TicketOrderLock implements Lock {

    AtomicInteger ticketNo = new AtomicInteger();
    AtomicInteger servNo = new AtomicInteger();
    ThreadLocal<Integer> threadLocal = new ThreadLocal<>();


    @Override
    public void lock() {
        int ticket = ticketNo.getAndIncrement();
        threadLocal.set(ticket);

        while (ticket != servNo.get()) {

        }

    }

    @Override
    public void unlock() {
        int ticket = threadLocal.get();
        servNo.compareAndSet(ticket, ticket + 1);
    }
}
