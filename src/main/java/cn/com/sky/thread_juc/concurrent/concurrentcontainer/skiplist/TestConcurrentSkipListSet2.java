package cn.com.sky.thread_juc.concurrent.concurrentcontainer.skiplist;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import org.junit.Test;

public class TestConcurrentSkipListSet2 {

	@Test
	public void skipListSetshow() {

		Set<Integer> mset = new ConcurrentSkipListSet<>();

		mset.add(1);
		mset.add(21);
		mset.add(6);
		mset.add(2);
		// 输出是有序的，从小到大。
		// skipListSet result=[1, 2, 6, 21]
		System.out.println("ConcurrentSkipListSet result=" + mset);

		Set<String> myset = new ConcurrentSkipListSet<>();
		System.out.println(myset.add("abc"));
		System.out.println(myset.add("fgi"));
		System.out.println(myset.add("def"));
		System.out.println(myset.add("Abc"));
		/*
		 * 输出是有序的:ConcurrentSkipListSet contains=[Abc, abc, def, fgi]
		 */
		System.out.println("ConcurrentSkipListSet contains=" + myset);
	}
}