package cn.com.sky.thread_patterns.thread_per_message;

/**
 * Thread-Per-Message 模式
 *
 * <pre>
 *
 * 适用的情况
 * 解决一个请求会花费比较长的时间, 这时候程序的主进程的控制权一直会被当前请求所占用, 其他的请求也无法进行处理.
 *
 * 实现的方式
 * 在接收请求的主线程方法中, 另外开启一个线程, 让新开启的线程对该请求进行处理, 这时候, 接收请求的主线程不会被阻塞到.
 *
 * 相关的模式
 * 想要节省开启线程所花费的时间, 可以使用Worker-Thread模式.
 * 当需要返回结果的时候, 可以使用Future模式.
 *
 *
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        Host host = new Host();
        try {
            host.request(50, 'A');
            host.request(50, 'B');
            host.request(50, 'C');
        } finally {
            System.out.println("main end");
        }
    }
}