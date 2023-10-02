package cn.com.sky.thread_juc.concurrent.concurrentcontainer.concurrenthashmap;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单线程
 */
public class TestHashMapAndHashTable {

	public static void main(String[] args) {

		HashMap<String, String> hashMap = new HashMap<>();
		Hashtable<String, String> hashTable = new Hashtable<>();
		Map<String, String> concurrentHashMap = new ConcurrentHashMap<>();

		int NUM = 100 * 1000 * 1000;


		for (int i = 0; i < NUM; i++) {
			hashMap.put("key", "value");
		}

		for (int i = 0; i < NUM; i++) {
			hashTable.put("key", "value");
		}

		for (int i = 0; i < NUM; i++) {
			concurrentHashMap.put("key", "value");
		}

	}

}
