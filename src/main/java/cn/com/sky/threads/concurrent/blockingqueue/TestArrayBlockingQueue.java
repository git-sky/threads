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
		ArrayBlockingQueue<Integer> bqueue = new ArrayBlockingQueue<>(20);
		for (int i = 0; i < 30; i++) {
			// 将指定元素添加到此队列中，如果没有可用空间，将一直等待（如果有必要）。
			try {
				bqueue.put(i);//阻塞等待,可中断
                bqueue.take();//阻塞获取,可中断
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
            System.out.println("put over ...");

            bqueue.add(i);
            System.out.println("add over ...");

            bqueue.offer(i);
            System.out.println("offer over ...");

            System.out.println("向阻塞队列中添加了元素:" + i);
		}
		System.out.println("over...");
	}
}