package cn.com.sky.threads.thread.other_method;


public class TestUncaughtException2 {
    public static void main(String[] args) {
        Task task = new Task();
        Thread thread = new Thread(task);
        thread.start();
    }
}

class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been capturedn");
        System.out.printf("Thread: %sn", t.getId());
        System.out.printf("Exception: %s: %sn", e.getClass().getName(), e.getMessage());
        System.out.printf("Stack Trace: n");
        e.printStackTrace(System.out);
        System.out.printf("Thread status: %sn", t.getState());
        new Thread(new Task()).start();
    }
}


class Task implements Runnable {
    @Override
    public void run() {
        Thread.currentThread().setUncaughtExceptionHandler(new ExceptionHandler());
        System.out.println(Integer.parseInt("123"));
        System.out.println(Integer.parseInt("234"));
        System.out.println(Integer.parseInt("345"));
        System.out.println(Integer.parseInt("XYZ")); //This will cause NumberFormatException
        System.out.println(Integer.parseInt("456"));
    }
}