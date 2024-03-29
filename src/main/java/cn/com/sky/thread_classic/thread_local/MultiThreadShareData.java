package cn.com.sky.thread_classic.thread_local;

/**
 * 2.多线程共享数据
 */
public class MultiThreadShareData {
    public static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 1;
        }
    };

    public static void main(String[] args) {
        System.out.println(Thread.currentThread() + "-" + Thread.currentThread().getName() + " shareData=>" + MultiThreadShareData.threadLocal.get());
        new Thread(new MA("MA")).start();
        new Thread(new MB("MB")).start();
    }
}

class MA implements Runnable {
    private String name;

    public MA(String name) {
        this.name = name;
    }

    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread() + "-" + this.name + " shareData=>" + MultiThreadShareData.threadLocal.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MB implements Runnable {
    private String name;

    public MB(String name) {
        this.name = name;
    }

    public void run() {
        try {
            Thread.sleep(2000);
            System.out.println(Thread.currentThread() + "-" + this.name + " shareData=>" + MultiThreadShareData.threadLocal.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
