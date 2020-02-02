package cn.com.sky.thread_patterns.single_threaded_execution;

/**
 * Single Threaded Execution 模式
 *
 * <pre>
 *
 * 适用的情况
 * 当多个线程同时对可变实例进行操作的情况下, 实例就会变成非线程安全.
 *
 * 实现的方式
 * Synchronized关键字
 * synchronized使得指定的方法或者代码块只能串行执行, 也就是同一时间段, 只能有一个线程能对synchronized锁定的地方进行访问.
 *
 * synchronized 声明在方法上
 * 默认锁定的对象是this
 * synchronized 声明在代码块上
 * 要指定要锁定的对象, 或者指定锁定的标志
 *
 * 多个线程同时访问synchonized加锁的方法或者代码段, 就会发生阻塞, 除了获得锁的线程, 其他线程会进入锁定对象的同步队列, 获得锁的线程完成相关任务后, 必须用notify() 随机唤醒一个等待队列里面的线程, 或者notifyAll()方法唤醒所有线程, 不能保证或预测重新获得锁的线程.
 *
 * 相关的模式
 * 当共享实例的数据不会发生变化的时候, 可以使用Immutable模式.
 * 读的次数要远远大于写的情况下, 可以进行读写分离, 使用Read-Write Lock模式
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Testing Gate, hit CTRL+C to exit");
        Gate gate = new Gate();
        new UserThread(gate, "Alice", "Alaska").start();
        new UserThread(gate, "Bobby", "Brazil").start();
        new UserThread(gate, "Chris", "Canada").start();
    }
}