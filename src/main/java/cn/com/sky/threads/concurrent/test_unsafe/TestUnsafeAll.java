package cn.com.sky.threads.concurrent.test_unsafe;

/**
 * 我试图将这些特性进行分类，可以得到如下5种使用场景：
 * <p>
 * 1.对变量和数组内容的原子访问，自定义内存屏障
 * AtomicX
 * AtomicXFieldUpdater
 *
 * 2.对序列化的支持
 * allocateInstance
 *
 * 3.自定义内存管理/高效的内存布局
 * allocateMemory
 *
 * 4.与原生代码和其他JVM进行互操作
 *
 * 5.对高级锁的支持
 */
public class TestUnsafeAll {


}
