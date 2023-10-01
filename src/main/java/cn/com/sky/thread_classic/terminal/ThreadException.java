package cn.com.sky.thread_classic.terminal;

/**
 * 发生未捕获异常，线程结束。 如果捕获之后，就可以继续执行。
 */
public class ThreadException extends Thread {

    @Override
    public void run() {
        while (true) {
            System.out.println("i am running.........");
            int zero = 0;
//            try {
            int a = 10 / zero;
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
            System.out.println("i am ok....");
        }
    }

    public static void main(String[] args) throws Exception {
        ThreadException t = new ThreadException();
        t.start();
        sleep(5000); // 主线程延迟5秒
        t.join();// 等待thread执行完毕，然后主线程继续向下执行。
        System.out.println("main线程退出!");
    }
}
