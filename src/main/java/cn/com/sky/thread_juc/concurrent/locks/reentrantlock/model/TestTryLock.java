package cn.com.sky.thread_juc.concurrent.locks.reentrantlock.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestTryLock {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();

		/**
		 * 尝试cas抢锁，抢锁失败则返回。
		 */
		if (lock.tryLock()) {
			try {
				// 处理任务
			} catch (Exception ex) {

			} finally {
				lock.unlock(); // 释放锁
			}
		} else {
			// 如果不能获取锁，则直接做其他事情
		}
	}
}
