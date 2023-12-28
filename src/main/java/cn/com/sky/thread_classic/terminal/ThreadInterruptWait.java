package cn.com.sky.thread_classic.terminal;

/**
 * 中断wait方法，结束线程。
 */
public class ThreadInterruptWait extends Thread {

    @Override
    public synchronized void run() {
        while (true) {
            try {
                System.out.println("before wait");
                this.wait();
                System.out.println("after wait");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
                //wait被中断时是会擦除中断标志的，抛异常的同时，该线程的中断状态会被清除。所以下面打印false。
                System.out.println("中断状态：" + Thread.currentThread().isInterrupted());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new ThreadInterruptWait();
        t.start();
        System.out.println("在按任意键中断线程!");
        System.in.read();
        t.interrupt();
        t.join();
        System.out.println("线程已经退出!");
    }
}
