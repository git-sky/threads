package cn.com.sky.thread_classic.thread_local.random_demo;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <pre>
 *
 *  避免Random实例被多线程使用，虽然共享该实例是线程安全的，但会因竞争同一seed 导致的性能下降。
 *  说明：Random实例包括java.util.Random 的实例或者 Math.random()实例。
 *  正例：在JDK7之后，可以直接使用API ThreadLocalRandom，在 JDK7之前，可以做到每个线程一个实例。
 *
 * </pre>
 */
public class TestThreadLocalRandom {
    public static void main(String[] args) {

        // 获取特定于当前线程的Random类实例。
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 100000; i++) {

//        int a = random.nextInt();
            int a = random.nextInt(1111, 9999);
            System.out.println(a);
        }
    }
}
