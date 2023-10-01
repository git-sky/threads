package cn.com.sky.thread_juc.concurrent.concurrentcontainer.concurrenthashmap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多个线程
 */
public class TestHashMapAndHashTable2 {

	public static void main(String[] args) {

//		 Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<Integer, Integer>());
		// Hashtable<Integer, Integer> map = new Hashtable<Integer, Integer>();
		Map<Integer, Integer> map = new ConcurrentHashMap<>();

		int count = 100 * 100 * 500;

		int n = 10;

		CountDownLatch latch = new CountDownLatch(n);

		ExecutorService service = Executors.newFixedThreadPool(n);

		CompareMap[] plays = new CompareMap[n];

		int avgcount = count / n;
		for (int i = 0; i < n; i++) {
			int begin_count = avgcount * i;
			int end_count = avgcount * (i + 1);
			plays[i] = new CompareMap(map, begin_count, end_count, latch);
		}

		for (CompareMap p : plays) {
			service.execute(p);
		}

		service.shutdown();

		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	static final class CompareMap extends Thread {
		private Map map;
		private int begin_count;
		private int end_count;
		private CountDownLatch latch;

		public CompareMap() {
		}

		CompareMap(Map map, int begin_count, int end_count, CountDownLatch latch) {
			this.map = map;
			this.begin_count = begin_count;
			this.end_count = end_count;
			this.latch = latch;

		}

		@Override
		public void run() {
			for (Integer i = begin_count; i < end_count; i++) {
				map.put(i, i);
			}
			latch.countDown();
		}
	}

}
