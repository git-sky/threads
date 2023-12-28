package cn.com.sky.thread_classic.interrupt;


/**
 * 中断状态与中断异常，互相转换。
 */
public class TestInterrupt {

    public static void main(String[] args) {

        Thread.currentThread().interrupt();

        try {
            interrupteToException();
        } catch (Exception e) {
            System.out.println("interrupteToException, e=" + e.getMessage());
        }

        Thread.currentThread().interrupt();

        try {
            delayThrowException();
        } catch (Exception e) {
            System.out.println("delayThrowException, e=" + e.getMessage());
        }

        Thread.currentThread().interrupt();
        try {
            exceptionToInterrupt();
        } catch (Exception e) {
            System.out.println("exceptionToInterrupt, e=" + e.getMessage());
        }


        exceptionToInterrupt();
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("exceptionToInterrupt isInterrupted...");
        }
    }


    public static void interrupteToException() throws InterruptedException {
        //中断状态转为中断异常
        if (Thread.interrupted()) {//清除异常标志
            throw new InterruptedException("i am interrupt");
        }
    }

    public static void exceptionToInterrupt() {
        //中断异常转为中断状态
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //sleep被中断时是会擦除中断标志的，抛异常的同时，该线程的中断状态会被清除。所以需要再次中断一次。
            System.out.println("interrupt");
            Thread.currentThread().interrupt();
        }
    }


    private static void delayThrowException() throws InterruptedException {
        //稍后抛出异常
        InterruptedException saveException = null;
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            saveException = e;
        }
        System.out.println("run....");
        if (saveException != null) {
            System.out.println("will throw saveException");
            throw saveException;
        }
    }
}