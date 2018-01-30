package cn.com.sky.threads.concurrent.copyonwrite;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *
 * CopyOnWriteArrayList适合使用在读操作远远大于写操作的场景里，比如缓存。
 * 
 * 发生修改时候做copy，新老版本分离，保证读的高性能，适用于以读为主的情况。
 * 
 * 读不加锁，写需要加锁。
 * 
 * opyOnWrite容器也是一种读写分离的思想，读和写不同的容器。
 * 
 * CopyOnWrite容器只能保证数据的最终一致性，不能保证数据的实时一致性。所以如果你希望写入的的数据，马上能读到，请不要使用CopyOnWrite容器。
 *
 * </pre>
 */
public class TestCopyOnWriteArrayList {
	/**
	 * 读线程
	 */
	private static class ReadTask implements Runnable {
		List<String> list;

		public ReadTask(List<String> list) {
			this.list = list;
		}

		public void run() {
			for (String s : list) {
				System.out.println("read:" + s);
			}
		}
	}

	/**
	 * 写线程
	 */
	private static class WriteTask implements Runnable {
		List<String> list;
		int index;

		public WriteTask(List<String> list, int index) {
			this.list = list;
			this.index = index;
		}

		public void run() {
			list.remove(index);
			list.add(index, "write_" + index);
			list.add("a");
		}
	}

	public void run() {
		final int NUM = 10;

		// List<String> list = new ArrayList<String>();

		CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();

		for (int i = 0; i < NUM; i++) {
			list.add("main_" + i);
			list.get(0);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(NUM);
		for (int i = 0; i < NUM; i++) {
			executorService.execute(new ReadTask(list));
			executorService.execute(new WriteTask(list, i));
		}
		executorService.shutdown();
	}

	public static void main(String[] args) {
		new TestCopyOnWriteArrayList().run();
	}
}