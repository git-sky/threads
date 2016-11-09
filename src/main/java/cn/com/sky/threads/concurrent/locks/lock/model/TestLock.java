package cn.com.sky.threads.concurrent.locks.lock.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			// 处理任务
		} catch (Exception ex) {

		} finally {
			lock.unlock(); // 释放锁
		}
	}
}
