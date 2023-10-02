package cn.com.sky.thread_juc.concurrent.concurrentcontainer.copyonwrite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCollectionsSync {

	public static void main(String[] args) {

//		Collections.synchronizedList();

		List<String> synList = Collections.synchronizedList(new ArrayList<>());
		CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>();

		final int NUM = 10;

		for (int i = 0; i < NUM; i++) {
			synList.add("main_" + i);
		}
		ExecutorService executorService = Executors.newFixedThreadPool(NUM);
		for (int i = 0; i < NUM; i++) {
			executorService.execute(new ReadTask(synList));
			executorService.execute(new WriteTask(synList, i));
		}
		executorService.shutdown();
		System.out.println("aaaaaaaaa");

	}

	/**
	 * 读线程
	 *
	 */
	private static class ReadTask implements Runnable {
		List<String> list;

		public ReadTask(List<String> list) {
			this.list = list;
		}

		public void run() {
			for (String str : list) {
				System.out.println(str);
			}
		}
	}

	/**
	 * 写线程
	 * 
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
		}
	}

}
