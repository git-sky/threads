package cn.com.sky.threads.concurrent.blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * <pre>
 * 
 * 阻塞队列
 * 有头指针和尾指针的 单链表结构
 *  
 * 原理：
 * 链表实现， 有两把锁：
 * 头部一把锁，一个条件变量，控制获取元素线程之间的互斥。
 * 尾部一把锁，一个条件变量，控制放入元素线程之间的互斥。
 * 
 * 
 */
public class TestLinkedBlockingQueue {

	public static void main(String args[]) {

		LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();

		for (int e = 0; e < 30; e++) {
			queue.add(e);
			 try {
				queue.put(e);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			queue.offer(e);
		}

		queue.iterator();
	}

}
