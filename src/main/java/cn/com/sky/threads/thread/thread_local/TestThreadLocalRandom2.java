package cn.com.sky.threads.thread.thread_local;

import java.util.concurrent.ThreadLocalRandom;

/**
 * <pre>
 * Java 7之前我们使用Math.random()产生随机数，使用原子变量来保存当前的种子，这样两个线程同时调用序列时得到的是伪随机数，而不是相同数量的两倍。
 * 
 * ThreadLocalRandom是JDK7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
 * ThreadLocalRandom不是直接用new实例化， 而是第一次使用其静态方法current()。
 * 
 * 从Math.random()改变到ThreadLocalRandom有如下好处：
 * 我们不再有从多个线程访问同一个随机数生成器实例的争夺。
 * 取代以前每个随机变量实例化一个随机数生成器实例，我们可以每个线程实例化一个。
 * 
 * 
 * 正如StringBuffer和StingBuilder一样，通过将线程安全放入其初始化部分，而不是在使用阶段，这就能够得到性能提升，
 * 另外一个例子是ThreadLocal和synchronized ，synchronized是在代码使用时加上同步，而使用ThreadLocal是每个线程一个实例，避免使用共享要引入同步。
 */
public class TestThreadLocalRandom2 {
	public static void main(String[] args) {
		ThreadLocalRandom.current().nextInt();
	}

}
