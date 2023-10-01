package cn.com.sky.threads.concurrent.atomic.basic;

import org.junit.Test;

import java.util.concurrent.atomic.LongAdder;

/**
 * <pre>
 *  
 * LongAdder是JDK1.8开始出现的，所提供的API基本上可以替换掉原先的AtomicLong。
 *
 * LongAdder所使用的思想就是热点分离，这一点可以类比一下ConcurrentHashMap的设计思想。
 *
 * 就是将value值分离成一个数组，当多线程访问时，通过hash算法映射到其中的一个数字进行计数。
 *
 * 而最终的结果，就是这些数组的求和累加。这样一来，就减小了锁的粒度。
 *
 *
 * 理论上，我们已经拥有了原子性数据的AtomicLong，但是AtomicLong在很高并发的情况下，使用CAS的效率不是很高，
 * LongAdder内部将一个long分成多个cell，每个线程可以对一个cell操作，如果需要取出long数据则求和即可，这样增强了在高并发情况下的效率。
 *
 *
 *
 *
 *
 * </pre>
 */
public class TestLongAdder {

    @Test
    public void test() {
        LongAdder longAdder = new LongAdder();
        longAdder.add(123);
        longAdder.increment();
        longAdder.decrement();
        longAdder.sum();
        System.out.println(longAdder.longValue());
    }
}