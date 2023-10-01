package cn.com.sky.thread_juc.concurrent.locks.lock.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestLock {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();

		lock.lock();
		try {
			// to do something
		} catch (Exception ex) {

		} finally {
			lock.unlock(); // 释放锁
		}
	}
}
