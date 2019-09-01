package cn.com.sky.threads.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * AbstractQueuedSynchronizer 内部是双向链表阻塞CLH队列，头节点head的prev和thread为null。
 */
public class TestAqs extends AbstractQueuedSynchronizer {

	private static final long serialVersionUID = 1L;

}
