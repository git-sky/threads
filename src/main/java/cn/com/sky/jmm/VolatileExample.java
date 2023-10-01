package cn.com.sky.jmm;


/**
 * <pre>
 *
 *  假设一个线程叫做“writer”，另外一个线程叫做“reader”。对变量v的写操作会等到变量x写入到内存之后，然后读线程就可以看见v的值。
 *  因此，如果reader线程看到了v的值为true，那么，它也保证能够看到在之前发生的写入42这个操作。而这在旧的内存模型中却未必是这样的。
 *
 *
 * </pre>
 */
public class VolatileExample {

    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v == true) {
            //uses x - guaranteed to see 42.
            System.out.println(x);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10000000; i++) {

            VolatileExample volatileExample = new VolatileExample();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    volatileExample.writer();
                }
            }).start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    volatileExample.reader();
                }
            }).start();

        }
    }
}