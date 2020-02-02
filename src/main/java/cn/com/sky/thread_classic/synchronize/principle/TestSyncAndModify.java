package cn.com.sky.thread_classic.synchronize.principle;

/**
 * <pre>
 *
 * 当我们使用sychronized锁住某个对象时，我们锁住的是这个引用本身，还是内存（堆）中的这个对象本身？
 * 对这个问题的一个延伸是，当我们在sychronized作用区域内，为这个引用附一个新值的时候，sychronized是否还有效？
 *
 * 先给出结论，sychronized锁住的是内存（堆）中的对象，当引用被附上新值的时候，则相当于旧对象的锁被释放。
 */
public class TestSyncAndModify implements Runnable {

    private A syncA;

    @Override
    public void run() {
        synchronized (syncA) {
            System.out.println(Thread.currentThread().getName());
            syncA = new A();// 在sychronized作用域内对syncA做了修改，使它指向了一个新的对象，所以当这句话执行完之后，第二个线程就可以运行。
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        }
    }

    static class A {
    }

    public static void main(String[] args) {
        TestSyncAndModify sync = new TestSyncAndModify();
        A testA = new A();
        sync.syncA = testA;
        Thread one = new Thread(sync);
        Thread two = new Thread(sync);
        one.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        two.start();
    }
}
