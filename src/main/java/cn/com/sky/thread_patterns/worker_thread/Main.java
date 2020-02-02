package cn.com.sky.thread_patterns.worker_thread;


/**
 * <pre>
 *
 * Worker Thread 模式
 *
 *
 * 多线程处理任务的两种模式：
 * 1、富线程轻任务 (基于角色)
 * 2、富任务轻线程 (基于任务)
 *
 *
 * 富任务轻线程(基于任务模式)：任务中保存了足够的信息（数据和方法都包含在其中），这是富任务轻线程的方式。
 * 任何线程执行该任务都没有问题。worker thread就是这种模式。
 * java.util.TimerTask与java.util.concurrent.FutureTask也是这个模式。
 *
 *
 * 设计思想：
 * 调用与执行分离思想，类似于Command模式。
 *
 *
 * 别名
 * Thread Pool
 * Background Thread
 *
 * 适用的情况
 * 为了提高响应性， 而经常开启新线程让他负责活动的处理，但是每次开启关闭线程都需要花费时间.
 *
 * 实现的方式
 * 在活动的开始就启动多个线程存放起来, 然后将请求发送给这些线程进行处理.这样就不用每次接受请求的时候再进行创建和关闭线程的工作了.
 *
 * 相关的模式
 * 在将工人线程的处理结果返回给调用的方法时候， 可以使用Future模式.
 * 将请求发送给线程池的缓冲区可以使用Producer-Consumer模式.
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        Channel channel = new Channel(5);
        channel.startWorkers();
        new ClientThread("Alice", channel).start();
        new ClientThread("Bobby", channel).start();
        new ClientThread("Chris", channel).start();
    }
}