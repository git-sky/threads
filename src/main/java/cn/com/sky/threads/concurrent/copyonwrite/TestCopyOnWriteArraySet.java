package cn.com.sky.threads.concurrent.copyonwrite;

import java.util.concurrent.CopyOnWriteArraySet;

public class TestCopyOnWriteArraySet {

	public static void main(String[] args) {

		CopyOnWriteArraySet<String> set = new CopyOnWriteArraySet<String>();
		set.add("a");

	}

}