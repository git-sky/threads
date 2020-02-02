package cn.com.sky.thread_classic;

import org.junit.Test;

/**
 * <pre>
 * 1、 main方法结束，子线程并不会结束。
 * 在 main 中创建的多线程是非守护线程模式，所以只要子线程未执行结束，main线程会处于等待状态，这时程序进程也不会结束。
 *
 * 2、test方法结束，子线程会结束（跟junit有关）。
 * 在junit环境中，创建的多线程是守护线程模式。在JUnit的@Test方法中启用多线程，新启动的线程会随着@Test主线程的死亡而死亡。
 *
 * </pre>
 */
public class TestMainAndJunit {

    @Test
    public void test() {
        new Thread(new TheThread1()).start();
        for (int i = 0; i < 5; i++) {
            new Thread(new TheThread2()).start();
        }

        //test主线程结束，子线程也会结束。
        System.out.println("test is over....");
    }

    public static void main(String[] args) {
        new Thread(new TheThread1()).start();
        for (int i = 0; i < 5; i++) {
            new Thread(new TheThread2()).start();
        }
        //main主线程结束，子线程并不会结束。
        System.out.println("main is over....");
    }

}

class TheThread1 implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("TheThread1 run...");
        }
    }
}

class TheThread2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                System.out.println("TheThread2 run...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


