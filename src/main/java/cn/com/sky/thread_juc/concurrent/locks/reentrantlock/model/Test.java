package cn.com.sky.thread_juc.concurrent.locks.reentrantlock.model;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

	public static void main(String[] args) {

		Lock lock = new ReentrantLock();
		
		lock.lock();
		lock.unlock();
		
		
		lock.tryLock();
		try {
			lock.tryLock(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try {
			lock.lockInterruptibly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		
		
		Condition condition = lock.newCondition();

		try {
			condition.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		try {
			condition.await(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		condition.awaitUninterruptibly();

		condition.signal();
		condition.signalAll();

	}
}
