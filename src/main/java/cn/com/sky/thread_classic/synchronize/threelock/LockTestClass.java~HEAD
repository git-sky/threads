package cn.com.sky.thread_classic.synchronize.threelock;

public class LockTestClass {
    // 用于类锁计数
    private static int i = 0;
    // 私有锁
    private Object object = new Object();

    /**
     * 无锁方法
     */
    public void noSynMethod(long threadID, ObjThread thread) {
        System.out.println("noSynMethod: class obj is " + thread + ", threadId is" + threadID);
    }

    /**
     * 对象锁方法1, synOnMethod
     */
    public synchronized void synOnMethod() {
        long start = System.currentTimeMillis();
        System.out.println("synOnMethod begins , time = " + start + "ms");
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("synOnMethod ends , elapsed: " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 对象锁方法2, synInMethod, 采用synchronized(this)加锁
     */
    public void synInMethod() {
        synchronized (this) {
            long start = System.currentTimeMillis();
            System.out.println("synInMethod begins , time = " + start + "ms");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("synInMethod ends , elapsed: " + (System.currentTimeMillis() - start) + "ms");
        }

    }

    /**
     * 对象锁方法3, synMethodWithObj, 采用synchronized(object)加锁
     */
    public void synMethodWithObj() {
        synchronized (object) {
            long start = System.currentTimeMillis();
            System.out.println("synMethodWithObj begins , time = " + start + "ms");
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("synMethodWithObj ends , elapsed: " + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * 类锁
     */
    public static synchronized void increment() {
        long start = System.currentTimeMillis();
        System.out.println("class synchronized. i = " + i + ", time = " + start + "ms");
        i++;
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("class synchronized ends , elapsed: " + (System.currentTimeMillis() - start) + "ms");
    }

    /**
     * 类锁2
     */
    public static void increment2() {
        synchronized (LockTestClass.class) {
            long start = System.currentTimeMillis();
            System.out.println("class synchronized. i = " + i + ", time = " + start + "ms");
            i++;
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("class synchronized ends , elapsed: " + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * 类锁3
     */
    public void increment3() {
        synchronized (LockTestClass.class) {
            long start = System.currentTimeMillis();
            System.out.println("class synchronized. i = " + i + ", time = " + start + "ms");
            i++;
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("class synchronized ends , elapsed: " + (System.currentTimeMillis() - start) + "ms");
        }
    }
}