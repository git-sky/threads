package cn.com.sky.thread_classic.terminal;

/**
 * 中断sleep方法，结束线程。
 */
public class ThreadInterruptSleep extends Thread {

    @Override
    public void run() {
        try {
            sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new ThreadInterruptSleep();
        t.start();

        System.out.println("在按任意键中断线程!");
        System.in.read();

        t.interrupt();

        t.join();

        System.out.println("线程已经退出!");
    }
}
