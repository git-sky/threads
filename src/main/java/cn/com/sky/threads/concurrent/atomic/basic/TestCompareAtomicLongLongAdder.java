package cn.com.sky.threads.concurrent.atomic.basic;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * <pre>
 *
 *  在并发比较低的时候，LongAdder和AtomicLong的效果非常接近。
 *
 *  但是当并发较高时，两者的差距会越来越大。
 *
 *  上图中在线程数为1000，每个线程循环数为100000时，LongAdder的效率是AtomicLong的6倍左右。
 *
 * </pre>
 */
public class TestCompareAtomicLongLongAdder {

    public static final int THREAD_COUNT = 1000;
    static ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
    static CompletionService<Long> completionService = new ExecutorCompletionService<Long>(pool);
    static final AtomicLong atomicLong = new AtomicLong(0L);
    static final LongAdder longAdder = new LongAdder();

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            completionService.submit(new Callable<Long>() {
                @Override
                public Long call() throws Exception {
                    for (int j = 0; j < 100000; j++) {
//						atomicLong.incrementAndGet();
                        longAdder.increment();
                    }
                    return 1L;
                }
            });
        }
        for (int i = 0; i < THREAD_COUNT; i++) {
            Future<Long> future = completionService.take();
            future.get();
        }
        System.out.println(longAdder.longValue());
//        System.out.println(atomicLong.get());
        System.out.println("耗时：" + (System.currentTimeMillis() - start));
        pool.shutdown();
    }
}

