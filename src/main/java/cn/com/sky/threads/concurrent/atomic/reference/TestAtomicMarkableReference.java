package cn.com.sky.threads.concurrent.atomic.reference;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * <pre>
 *
 * 维护带有标记位的对象引用，可以原子方式对其进行更新。
 *
 * AtomicMarkableReference类描述的一个<Object,Boolean>的对，可以原子的修改Object或者Boolean的值，这种数据结构在一些缓存或者状态描述中比较有用。
 * 这种结构在单个或者同时修改Object/Boolean的时候能够有效的提高吞吐量。
 *
 * </pre>
 */
public class TestAtomicMarkableReference {

    private static AtomicMarkableReference<Integer> atomicMarkableReference = new AtomicMarkableReference<Integer>(100, true);


    public static void main(String[] args) {

        Thread refT1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                }

                atomicMarkableReference.compareAndSet(100, 101, true, false);
                atomicMarkableReference.compareAndSet(101, 100, false, true);
            }
        });

        Thread refT2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                }
                boolean c3 = atomicMarkableReference.compareAndSet(100, 101, true, false);
                System.out.println(c3); // false
            }
        });

        refT1.start();
        refT2.start();
    }

}
