package cn.com.sky.thread_classic.synchronize.principle;

/**
 * <pre>
 *
 * 虽然是两个线程中，sychronized锁住的是不同的引用，一个是Base一个是SyncObject，
 * 但由于存在继承关系，在main函数中，我们让Base对象也指向了SyncObject内存区域 。
 * 因为sychronized锁住的是内存（堆）中的对象。这样，就形成了两个不同线程之间的竞争，因此后者被阻塞。
 *
 * </pre>
 */
public class ThreadUseBase extends Thread {

    private Base baseObject;

    public ThreadUseBase(Base boj) {
        baseObject = boj;
    }

    public void testSyncBase() {
        synchronized (baseObject) {
            System.out.println("enter thread use base");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("leave thread use base");
        }
    }

    @Override
    public void run() {
        testSyncBase();
    }

    static class ThreadUseChild extends Thread {
        private SyncObject childObj;

        public ThreadUseChild(SyncObject sobj) {
            childObj = sobj;
        }

        public void testSyncChild() {
            synchronized (childObj) {
                System.out.println("enter thread use child");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("leave thread use child");
            }
        }

        @Override
        public void run() {
            testSyncChild();
        }
    }

    static class Base {
    }

    static class SyncObject extends Base {
    }

    public static void main(String[] args) {
        SyncObject childObj = new SyncObject();
        Base baseObj = childObj;
        // Base baseObj = new Base();
        ThreadUseBase threadBase = new ThreadUseBase(baseObj);
        ThreadUseChild threadChild = new ThreadUseChild(childObj);
        threadBase.start();
        threadChild.start();
    }

}
