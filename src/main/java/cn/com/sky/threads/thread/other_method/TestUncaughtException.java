package cn.com.sky.threads.thread.other_method;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * Thread的run方法是不抛出任何检查型异常(checked exception)的,但是它自身却可能因为一个异常而被终止，导致这个线程的终结。最麻烦的是，在线程中抛出的异常即使使用try...catch也无法截获，因此可能导致一些问题出现，比如异常的时候无法回收一些系统资源，或者没有关闭当前的连接等等。
 * <p>
 * <p>
 * 未捕获异常处理器 UncaughtExceptionHandler
 */
public class TestUncaughtException {

    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadTest1());
//        thread.setUncaughtExceptionHandler(new MyExceptionHandler());
        thread.setName("mythread");
        thread.start();
    }
}

class ThreadTest1 implements Runnable {
    public void run() {
        System.out.println(10 / 0);
    }
}

class MyExceptionHandler implements UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {// 在这你可以记录相关错误日志到文件中
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread:%s\n", t.getName());
        System.out.printf("Exception: %s: %s:\n", e.getClass().getName(), e.getMessage());
        System.out.printf("Stack Trace:\n");
//		e.printStackTrace();
        System.out.printf("Thread status:%s\n", t.getState());
    }
}