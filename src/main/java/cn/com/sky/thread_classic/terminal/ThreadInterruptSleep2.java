package cn.com.sky.thread_classic.terminal;

/**
 * 中断sleep方法。
 */
public class ThreadInterruptSleep2 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("执行run方法");
            try {
                Thread.sleep(2000);
                System.out.println("线程完成休眠" + i);
            } catch (Exception e) {
                System.out.println("休眠被打断" + i);
                // return; // 返回到程序的调用处
            }
            System.out.println("线程正常终止" + i);
        }
    }

    public static void main(String[] args) {
        ThreadInterruptSleep2 he = new ThreadInterruptSleep2();
        Thread demo = new Thread(he, "线程");
        demo.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main....");
        demo.interrupt(); // 2s后中断线程
        System.out.println(demo.isInterrupted());

        // Thread.interrupted();
        // demo.isInterrupted();
        // demo.interrupted();
        // demo.interrupt();
    }
}