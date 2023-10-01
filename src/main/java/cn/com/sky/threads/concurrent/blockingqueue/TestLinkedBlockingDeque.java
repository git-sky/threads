package cn.com.sky.threads.concurrent.blockingqueue;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * <pre>
 * 
 * 双端队列（deque，全名double-ended queue）是一种具有队列和栈的性质的数据结构。
 * 双端队列中的元素可以从两端弹出，其限定插入和删除操作在表的两端进行。
 * 
 * 
 * LinkedBlockingDeque：一个由双向链表结构组成的双向阻塞队列。
 * 有头指针和尾指针的 双向链表结构。一把锁，两个条件变量。
 * 
 * 
 * 从结果可以看到，程序并没结束，而是阻塞住了，原因是栈已经满了，后面追加元素的操作都被阻塞了。
 * 
 */
public class TestLinkedBlockingDeque {
	public static void main(String[] args) {
		BlockingDeque<Integer> bDeque = new LinkedBlockingDeque<>(20);
		for (int i = 0; i < 30; i++) {
			// 将指定元素添加到此阻塞栈中，如果没有可用空间，将一直等待（如果有必要）。
			try {
				bDeque.putFirst(i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("向阻塞栈中添加了元素:" + i);
		}
		System.out.println("over ...");
	}
}