package cn.com.sky.threads.thread.terminal;

/**
 * 发生未捕获异常，线程结束。 如果捕获之后，就可以继续执行。
 */
public class ThreadException extends Thread {

    public void run() {
        while (true) {
            System.out.println("i am running.........");
            int zero = 0;
            try {
                int a = 10 / zero;
            } catch (Exception e) {
                throw new Exception(e);
            }
            System.out.println("i am ok....");
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadException thread = new ThreadException();
        thread.start();
        sleep(5000); // 主线程延迟5秒
        thread.join();// 等待thread执行完毕，然后主线程继续向下执行。
        System.out.println("main线程退出!");
    }
}
