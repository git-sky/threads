package cn.com.sky.thread_juc.concurrent.atomic.basic;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * <pre>
 * JDK中提供了Unsafe.putOrderedObject，Unsafe.putOrderedInt，Unsafe.putOrderedLong这三个方法，JDK会在执行这三个方法时插入StoreStore内存屏障，避免发生写操作重排序。
 *
 * 而在Intel 64/IA-32架构下，StoreStore屏障并不需要，Java编译器会将StoreStore屏障去除。比起写入volatile变量之后执行StoreLoad屏障的巨大开销，采用这种方法除了避免重排序而带来的性能损失以外，不会带来其它的性能开销。
 */


public class TestAtomicBoolean {


    public static void main(String[] args) {

        AtomicBoolean ab = new AtomicBoolean();

//        Atomically sets the value to the given updated value if the current value {@code ==} the expected value.
        System.out.println(ab.compareAndSet(false, true));
        System.out.println(ab.compareAndSet(true, false));
        System.out.println(ab.get());
        System.out.println(ab.toString());

    }

}
