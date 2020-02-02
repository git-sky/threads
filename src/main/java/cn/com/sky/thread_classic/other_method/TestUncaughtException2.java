package cn.com.sky.thread_classic.other_method;

/**
 * <pre>
 *
 * Thread.UncaughtExceptionHandler 接口使得我们能够侦测到线程运行过程中抛出的未捕获的异常，以便做出相应的补救措施，
 *
 * 例如：创建并启动相应的替代线程。一个线程在其抛出未捕获的异常而终止前，总有一个UncaughtExceptionHandler实例会被选中。
 *
 * 被选中的 UncaughtExceptionHandler实例的 uncaughtException方法会被该线程在其终止前执行。
 *
 * UncaughtExceptionHandler 实例选择的优先级：线程实例本身关联的UncaughtExceptionHandler实例 》线程所在线程组》默认 UncaughtExceptionHandler。
 *
 *
 *
 * </pre>
 */
public class TestUncaughtException2 {

    public static void main(String[] args) {
        Thread thread = new Thread(new Task());
        thread.setName("task");
        thread.start();
    }
}

class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.printf("An exception has been captured\n");
        System.out.printf("Thread: %s %s\n", t.getId(), t.getName());
        System.out.printf("Exception: %s: %s\n", e.getClass().getName(), e.getMessage());
        System.out.printf("Stack Trace: \n");
//            e.printStackTrace(System.out);
        System.out.printf("Thread status: %s\n", t.getState());
        //创建并启动一个替代线程。
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