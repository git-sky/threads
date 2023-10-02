package cn.com.sky.thread_classic.volatiles;

public class VisibilityTest extends Thread {

    private volatile boolean stop = false;//不加volatile会有内存可见性问题，导致死循环。

    public void run() {
        int i = 0;
        while (!stop) {
            i++;
            System.out.println("...........");//不加volatile，写上这一句，会结束死循环，不知道什么原因？？
        }
        System.out.println("finish loop,i=" + i);
    }

    public void stopIt() {
        stop = true;
    }

    public boolean getStop() {
        return stop;
    }

    public static void main(String[] args) throws Exception {
        VisibilityTest v = new VisibilityTest();
        v.start();

        Thread.sleep(1000);
        v.stopIt();
        Thread.sleep(2000);
        System.out.println("finish main");
        System.out.println(v.getStop());
    }
}
