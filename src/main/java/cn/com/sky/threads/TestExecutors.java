package cn.com.sky.threads;

import java.util.concurrent.Executors;

public class TestExecutors {
	
	public static void main(String[] args) {
		Executors.newSingleThreadExecutor();
		Executors.newCachedThreadPool();
		Executors.newFixedThreadPool(10);
	}

}
