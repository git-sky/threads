package cn.com.sky.thread_juc.concurrent.concurrentcontainer.copyonwrite;

import java.util.concurrent.CopyOnWriteArraySet;

public class TestCopyOnWriteArraySet {

	public static void main(String[] args) {

		CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<>();
		set.add("a");

	}

}