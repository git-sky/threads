package cn.com.sky.thread_classic.terminal;

/**
 * 1.使用标志位状态,使线程终止。
 */
public class ThreadFlag extends Thread {
    private volatile boolean exit = false;

    @Override
    public void run() {
        while (!exit) {
            System.out.println("i am running.........");
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadFlag thread = new ThreadFlag();
        thread.start();
        sleep(3000); // 主线程延迟3秒
        thread.exit = true; // 终止线程thread
        thread.join();
        System.out.println("main线程退出!");
    }
}
