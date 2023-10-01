package cn.com.sky.thread_classic.other_method;

/**
 * <pre>
 *
 * sleep和wait区别：
 * 1.sleep是Thread静态方法，wait是Object方法。
 * 2.sleep线程暂停，但是不释放锁；wait线程暂停，释放锁。都会释放CPU资源。
 * 3.sleep可以不在synchronized中使用，wait必须在synchronized中使用。
 *
 * 相同点：
 * 都会释放CPU资源。
 *
 * </pre>
 */
public class TestThreadSleep {

    public static void main(String[] args) {
        System.out.println("main...............开始");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main...............结束");

    }
}
