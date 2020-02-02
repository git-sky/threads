package cn.com.sky.thread_juc.thread_pool.scheduledThreadPoolExecutor;

import java.util.Timer;
import java.util.TimerTask;

/**
 * <pre>
 *
 * 如果TimerTask抛出未检查的异常，Timer将会产生无法预料的行为。
 * Timer线程并不捕获异常，所以TimerTask抛出的未检查的异常会终止timer线程。
 * 这种情况下，Timer也不会再重新恢复线程的执行了;它错误的认为整个Timer都被取消了。
 * 此时，已经被安排但尚未执行的TimerTask永远不会再执行了，新的任务也不能被调度了。
 */
public class TimerException2 {
    private Timer timer = new Timer();

    // 启动计时器
    public void lanuchTimer() {
        timer.schedule(new TimerTask() {
            public void run() {
                throw new RuntimeException();
            }
        }, 1000 * 3, 500);
    }

    // 向计时器添加一个任务
    public void addOneTask() {
        timer.schedule(new TimerTask() {
            public void run() {
                System.out.println("hello world");
            }
        }, 1000 * 1, 1000 * 5);
    }

    public static void main(String[] args) throws Exception {
        TimerException2 test = new TimerException2();
        test.lanuchTimer();
        Thread.sleep(1000 * 5);// 5秒钟之后添加一个新任务
        test.addOneTask();
    }
}