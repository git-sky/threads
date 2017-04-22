package cn.com.sky.threads.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * <pre>
 * 
 *  阻塞队列
 *  
 *  ArrayBlockingQueue：
 *  
 *  规定大小的BlockingQueue，其构造函数必须带一个int参数来指明其大小。其所含的对象是以FIFO（先入先出）顺序排序的。
 *  
 * 实现原理：
 * 循环数组实现,有一把锁lock，两个条件变量notEmpty，notFull,控制数组中元素的获取和加入。takeIndex,putIndex获取元素的索引和存放元素的索引。
 * 
 */
public class TestArrayBlockingQueue {
	public static void main(String[] args) {
		ArrayBlockingQueue<Integer> bqueue = new ArrayBlockingQueue<Integer>(20);
		for (int i = 0; i < 30; i++) {
			// 将指定元素添加到此队列中，如果没有可用空间，将一直等待（如果有必要）。
			try {
				bqueue.put(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bqueue.add(i);
			bqueue.offer(i);
			System.out.println("向阻塞队列中添加了元素:" + i);
		}
		System.out.println("程序到此运行结束......");
	}
}