package cn.com.sky.thread_juc.concurrent.locks.reentrantlock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * <pre>
 * 
 * lock的使用
 * 
 * 位置1是正确的位置。
 * 
 * 位置2是错误的位置。
 */
public class TestLock {

	Lock lock = new ReentrantLock();// 位置1

	public static void main(String[] args) {
		final TestLock test = new TestLock();

		Thread t1 = new Thread() {
			public void run() {
				test.add(Thread.currentThread());
			};
		};
		t1.setName("t1");
		t1.start();
		

		Thread t2 = new Thread() {
			public void run() {
				test.add(Thread.currentThread());
			};
		};
		t2.setName("t2");
		t2.start();
		
		t1.interrupt();
		t2.interrupt();

	}

	public void add(Thread thread) {
		// Lock lock = new ReentrantLock(); //
		// 位置2，注意这个地方,lock是局部变量。每个线程执行到lock.lock()处获取的是不同的锁，所以就不会互斥。
	
		lock.lock();
		try {
			System.out.println(thread.getName() + " get lock");
//			Thread.sleep(3000);
			for(int i=0;i<500000000;i++){
				ArrayList<Integer> list=new ArrayList<>();
				list.add(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(thread.getName() + " release lock");
			lock.unlock();
		}
	}
}