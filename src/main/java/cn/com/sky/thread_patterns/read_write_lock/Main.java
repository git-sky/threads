package cn.com.sky.thread_patterns.read_write_lock;

/**
 * Read-Write Lock 模式
 *
 * <pre>
 *
 *     适用的情况
 * 多个线程共享了实例, 实例 是可变的, 对实例进行读的次数大于写的次数.多个线程可以同时读, 但一段时间内只能有一个线程可以进行写.
 *
 * 实现的方式
 * 引入一个ReadWriteLock角色管理前来读和写的线程, 进行互斥处理.
 *
 * 相关的模式
 * ReadWriteLock进行互斥的部分使用的是Guarded Suspension模式.
 * 当实例是不可变的情况下, 可以使用Immutable模式.
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        Data data = new Data(10);
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new ReaderThread(data).start();
        new WriterThread(data, "ABCDEFG").start();
        new WriterThread(data, "abcdefg").start();
    }
}