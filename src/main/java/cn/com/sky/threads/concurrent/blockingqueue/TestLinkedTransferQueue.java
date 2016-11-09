package cn.com.sky.threads.concurrent.blockingqueue;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * <pre>
 * 
 * TransferQueue继承了BlockingQueue（BlockingQueue又继承了Queue）并扩展了一些新方法。
 * BlockingQueue（和Queue）是Java 5中加入的接口，它是指这样的一个队列：当生产者向队列添加元素但队列已满时，生产者会被阻塞；当消费者从队列移除元素但队列为空时，消费者会被阻塞。
 * 
 * TransferQueue则更进一步，生产者会一直阻塞直到所添加到队列的元素被某一个消费者所消费（不仅仅是添加到队列里就完事）。新添加的transfer方法用来实现这种约束。
 * 顾名思义，阻塞就是发生在元素从一个线程transfer到另一个线程的过程中，它有效地实现了元素在线程之间的传递（以建立Java内存模型中的happens-before关系的方式）。
 * 
 * TransferQueue还包括了其他的一些方法：两个tryTransfer方法，一个是非阻塞的，另一个带有timeout参数设置超时时间的。
 * 还有两个辅助方法hasWaitingConsumer()和getWaitingConsumerCount()。
 * 
 * 当我第一次看到TransferQueue时，首先想到了已有的实现类SynchronousQueue。SynchronousQueue的队列长度为0，最初我认为这好像没多大用处，
 * 但后来我发现它是整个Java Collection Framework中最有用的队列实现类之一，特别是对于两个线程之间传递元素这种用例。
 * 
 * TransferQueue相比SynchronousQueue用处更广、更好用，因为你可以决定是使用BlockingQueue的方法（例如put方法）还是确保一次传递完成（ 即transfer方法）。
 * 在队列中已有元素的情况下，调用transfer方法，可以确保队列中被传递元素之前的所有元素都能被处理。
 * Doug Lea说从功能角度来讲，LinkedTransferQueue实际上是ConcurrentLinkedQueue、SynchronousQueue（公平模式）和LinkedBlockingQueue的超集 。
 * 而且LinkedTransferQueue更好用，因为它不仅仅综合了这几个类的功能，同时也提供了更高效的实现。
 * 
 */
public class TestLinkedTransferQueue {

	public static void main(String[] args) {

		TransferQueue<String> queue = new LinkedTransferQueue<String>();

		try {
			queue.transfer("element");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
