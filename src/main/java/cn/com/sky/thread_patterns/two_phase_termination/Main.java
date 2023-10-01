package cn.com.sky.thread_patterns.two_phase_termination;

/**
 * <pre>
 * Two-Phase Termination模式
 *
 * 类似 ExecutorService的shutdown 方法
 *
 *
 * 适用的情况
 * 当想要终止正在运行的线程, 如果突然被紧急终止了, 那么这时候的实例的状态可能就会出现错误.
 *
 * 实现的方式
 * 可能会被中断的线程, 轮询线程的状态或者捕获InterruptException进行处理, 利用finally{}确保线程关闭的时候维护相关状态的安全.
 *
 * 相关的模式
 * 当想在执行终止处理前禁止其他处理, 可以使用Balking模式.
 *
 * 代码示例:
 * 这是一个负责慢慢累加的CountUpThread"线程"类, 他会在接收到终止通知的时候, 打印最后counter的大小.
 *
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("main : start");
        try {
            CountUpThread t = new CountUpThread();
            t.start();

            Thread.sleep(10000);
            System.out.println("main : shutdownRequest");
            t.shutdownRequest();


            System.out.println("main : join");
            t.join();
            System.out.println("main: isShutdownRequested " + t.isShutdownRequested());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main : end");
    }
}