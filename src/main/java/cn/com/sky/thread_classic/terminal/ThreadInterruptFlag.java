package cn.com.sky.thread_classic.terminal;

/**
 * 根据中断状态，结束线程。
 */
public class ThreadInterruptFlag extends Thread {

    @Override
    public void run() {
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(i++);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //sleep被中断时是是会擦除中断标志的，抛异常的同时，该线程的中断状态会被清除。所以下面打印false。
                System.out.println("sleep被中断之后的中断状态：" + Thread.currentThread().isInterrupted());//false
                //再次中断
                Thread.currentThread().interrupt();
                System.out.println("重新中断的结果：" + Thread.currentThread().isInterrupted());
            }
        }

        // isInterrupted不会清除中断状态,检测中断状态（ private native boolean isInterrupted(false);）
        System.out.println("1 isInterrupted:" + Thread.currentThread().isInterrupted());
        System.out.println("2 isInterrupted:" + Thread.currentThread().isInterrupted());

        // interrupted方法会清除中断状态，检测中断状态（ private native boolean isInterrupted(true);）
        System.out.println("3 interrupted:" + Thread.interrupted());// true
        System.out.println("4 interrupted:" + Thread.interrupted());// false

        System.out.println("5 isInterrupted:" + Thread.currentThread().isInterrupted());

        Thread.currentThread().interrupt();//执行线程interrupt事件

        // isInterrupted不会清除中断状态,检测中断状态（ private native boolean isInterrupted(false);）
        System.out.println("6 isInterrupted:" + Thread.currentThread().isInterrupted());
        System.out.println("7 isInterrupted:" + Thread.currentThread().isInterrupted());

        // interrupted方法会清除中断状态，检测中断状态（ private native boolean isInterrupted(true);）
        System.out.println("8 interrupted:" + Thread.interrupted());// true
        System.out.println("9 interrupted:" + Thread.interrupted());// false
        System.out.println("10 isInterrupted:" + Thread.currentThread().isInterrupted());


    }

    public static void main(String[] args) throws Exception {
        Thread t = new ThreadInterruptFlag();
        t.start();
        System.out.println("在按任意键中断线程!");
        System.in.read();
        t.interrupt();
        t.join();
        System.out.println("线程已经退出!");
    }
}
