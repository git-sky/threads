package cn.com.sky.thread_juc.concurrent.atomic.basic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <pre>
 * 
 * 原子更新基本类型类
 * 
 * 用于通过原子的方式更新基本类型，Atomic包提供了以下三个类：
 * 
 * AtomicBoolean：原子更新布尔类型。
 * AtomicInteger：原子更新整型。
 * AtomicLong：原子更新长整型。
 * 
 * 
 * AtomicInteger的常用方法如下：
 * 
 * int addAndGet(int delta) ：以原子方式将输入的数值与实例中的值（AtomicInteger里的value）相加，并返回结果 boolean
 * compareAndSet(int expect, int update) ：如果输入的数值等于预期值，则以原子方式将该值设置为输入的值。
 * int getAndIncrement()：以原子方式将当前值加1，注意：这里返回的是自增前的值。
 * void lazySet(int newValue)：最终会设置成newValue，使用lazySet设置值后
 * ，可能导致其他线程在之后的一小段时间内还是可以读到旧的值。关于该方法的更多信息可以参考并发网翻译的一篇文章《AtomicLong.lazySet是如何工作的？》
 * int getAndSet(int newValue)：以原子方式设置为newValue的值，并返回旧值。
 * 
 * </pre>
 * 
 */
public class TestAtomicInteger4 {

	static AtomicInteger ai = new AtomicInteger(1);

	public static void main(String[] args) {
		System.out.println(ai.getAndIncrement());
		System.out.println(ai.get());
	}

}
