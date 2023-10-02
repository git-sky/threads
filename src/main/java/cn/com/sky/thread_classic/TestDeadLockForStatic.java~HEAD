package cn.com.sky.thread_classic;

/**
 * 不同实例共享一个静态属性，所以还是会有锁的竞争。
 */
public class TestDeadLockForStatic implements Runnable {

    public int flag = 1;
    static Object o1 = new Object(), o2 = new Object();//静态变量，所有实例共享，所以会造成死锁。

    public void run() {
        System.out.println("flag=" + flag);
        if (flag == 1) {
            synchronized (o1) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o2) {
                    System.out.print("1");

                }
            }
        }
        if (flag == 0) {
            synchronized (o2) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                synchronized (o1) {
                    System.out.print("0");
                }
            }
        }
    }

    public static void main(String args[]) {
        TestDeadLockForStatic td1 = new TestDeadLockForStatic();
        TestDeadLockForStatic td2 = new TestDeadLockForStatic();
        td1.flag = 1;
        td2.flag = 0;
        Thread t1 = new Thread(td1);
        Thread t2 = new Thread(td2);
        t1.start();
        t2.start();
    }

}
