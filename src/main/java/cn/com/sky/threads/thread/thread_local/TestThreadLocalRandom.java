package cn.com.sky.threads.thread.thread_local;

import java.util.concurrent.ThreadLocalRandom;

public class TestThreadLocalRandom {
	public static void main(String[] args) {

		// 获取特定于当前线程的Random类实例。
		ThreadLocalRandom random = ThreadLocalRandom.current();

		int a = random.nextInt();
		System.out.println(a);
	}
}
