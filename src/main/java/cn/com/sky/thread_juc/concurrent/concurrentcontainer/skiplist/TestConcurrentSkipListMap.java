package cn.com.sky.thread_juc.concurrent.concurrentcontainer.skiplist;

import java.util.concurrent.ConcurrentSkipListMap;

/**
 * <pre>
 * 
 * ConcurrentSkipListMap提供了一种线程安全的并发访问的排序映射表。
 * 
 * 内部是SkipList（跳表）结构实现，在理论上能够在O（log（n））时间内完成查找、插入、删除操作。
 * 
 * </pre>
 */
public class TestConcurrentSkipListMap {

	public static void main(String[] args) {
		show();
	}

	public static void show() {

		ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

		map.put(1, "1");
		map.put(23, "23");
		map.put(3, "3");
		map.put(2, "2");
        map.put(33, "2");

		/*
		 * 输出是有序的，从小到大。 output 1 2 3 23
		 */
		for (Integer key : map.keySet()) {
			System.out.println(key+"->"+map.get(key));
		}
	}
}
