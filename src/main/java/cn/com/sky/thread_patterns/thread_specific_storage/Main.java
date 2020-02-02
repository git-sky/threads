package cn.com.sky.thread_patterns.thread_specific_storage;

/**
 * Thread-Specific Storage 模式
 *
 * <pre>
 *
 *     别名
 * Per-Thread Attribute
 * Thread-Specific Data
 * Thread-Specific Field
 * Thread-Local Storage
 *
 * 适用的情况
 * 使每个线程拥有独立的上下文实例. 从而避免了多线程之间的实例竞争.
 *
 * 实现的方式
 * java.lang.ThreadLocal来保管相关的所有线程的单独实例.
 *
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        new ClientThread("alice").start();
        new ClientThread("bob").start();
        new ClientThread("chris").start();
    }
}