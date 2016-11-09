package cn.com.sky.threads.thread.other_method;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 未捕获异常处理器 UncaughtExceptionHandler
 */
public class TestUncaughtException {

	public static void main(String[] args) {
		Thread thread = new Thread(new ThreadTest1());
		thread.setUncaughtExceptionHandler(new MyExceptionHandler());
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
		e.printStackTrace();
		System.out.printf("Thread status:%s\n", t.getState());
	}
}