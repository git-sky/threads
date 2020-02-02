package cn.com.sky.thread_patterns.guarded_suspension;

/**
 * Guarded Suspension 模式
 *
 * <pre>
 *
 *  适用的情况
 * 相比于Single Thread Exception模式, 本模式加入了加入了守护条件来确保共享实例在被线程访问前是正确的状态
 *
 * 实现的方式
 * 如果实例是不正确的, 那么就让前来访问的线程执行wait(), 在线程恢复到正确状态的时候, 由持有锁的进程来唤醒等待的线程继续访问被守护的实例.
 * 线程恢复到正确状态的时候, 一定要执行notify()或notifyAll(),否则等待的线程将永远无法被唤醒!
 *
 * 相关的模式
 * Single Thread Exception模式的升级版, 增加了守护条件!
 * 如果希望在实例是非正确的条件下, 来访问的线程不进行等待, 而是直接返回, 可以使用Balking模式
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        RequestQueue requestQueue = new RequestQueue();
        new ClientThread("alice", 5451L, requestQueue).start();
        new ServerThread("bob", 1651531L, requestQueue).start();
    }
}