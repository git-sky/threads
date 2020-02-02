package cn.com.sky.thread_classic.terminal;

/**
 * interrupt()方法只是修改中断的状态，并不会让线程终止。需要自己判断，进行处理。
 */
public class ThreadInterrupt extends Thread {

    @Override
    public void run() {
        while (true) {
            // isInterrupted不会清除中断状态,检测中断状态（ private native boolean isInterrupted(false);）
            System.out.println("isInterrupted:" + Thread.currentThread().isInterrupted());
            System.out.println("isInterrupted:" + Thread.currentThread().isInterrupted());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Thread t = new ThreadInterrupt();
        t.start();
        System.out.println("在按任意键中断线程!");
        System.in.read();
        t.interrupt();//执行线程interrupt事件
        t.join();
        System.out.println("线程已经退出!");
    }
}
