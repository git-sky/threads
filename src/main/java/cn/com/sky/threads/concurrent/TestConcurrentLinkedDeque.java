package cn.com.sky.threads.concurrent;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * 
 * <pre>
 * 
 * 非阻塞队列：
 * 
 * ConcurrentLinkedDeque，非阻塞双端队列，链表实现
 * 
 * ConcurrentLinkedQueue，非阻塞队列，链表实现
 * 
 */
public class TestConcurrentLinkedDeque {

	public static void main(String[] args) {
		ConcurrentLinkedDeque<Object> clq = new ConcurrentLinkedDeque<Object>();
		clq.add("a");
	}

}
