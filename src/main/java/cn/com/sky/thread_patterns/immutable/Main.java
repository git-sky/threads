package cn.com.sky.thread_patterns.immutable;

/**
 * <pre>
 * Immutable 模式
 *
 * 适用的情况
 * 多个线程之间共享对象, 但是该对象的状态不会发生变化
 * 用于提高Single Thread Exception模式的吞吐量
 *
 * 实现的方式
 * 使对象的状态不可发生变化, 从而使其线程安全
 * private / final 关键字
 *
 * 相关的方式
 * 对象的状态是可以改变的, 那么可以使用Single Thread Exception模式
 * 读的次数要远远大于写的情况下, 可以进行读写分离, 使用Read-Write Lock模式
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        Person alice = new Person("Alice", "Alaska");
        new PrintPersonThread(alice).start();
        new PrintPersonThread(alice).start();
        new PrintPersonThread(alice).start();
    }
}