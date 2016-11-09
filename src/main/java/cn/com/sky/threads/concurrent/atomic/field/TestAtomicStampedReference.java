package cn.com.sky.threads.concurrent.atomic.field;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * <pre>
 * 
 * 解决ABA问题
 * 
 * AtomicStampedReference类维护带有整数“标志”的对象引用，可以用原子方式对其进行更新。
 * 对比AtomicMarkableReference类的<Object,Boolean>， AtomicStampedReference维护的是一种类似<Object,int>的数据结构，其实就是对对象（引用）的一个并发计数。
 * 但是与AtomicInteger不同的是，此数据结构可以携带一个对象引用（Object），并且能够对此对象和计数同时进行原子操作。
 * 
 * </pre>
 * 
 */
public class TestAtomicStampedReference {

	private static AtomicInteger atomicInt = new AtomicInteger(100);

	private static AtomicStampedReference<Integer> atomicStampedRef = new AtomicStampedReference<Integer>(100, 0);

	public static void main(String[] args) throws InterruptedException {

		Thread intT1 = new Thread(new Runnable() {
			@Override
			public void run() {
				atomicInt.compareAndSet(100, 101);
				atomicInt.compareAndSet(101, 100);
			}
		});

		Thread intT2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}
				boolean c3 = atomicInt.compareAndSet(100, 101);
				System.out.println(c3); // true
			}
		});

		intT1.start();
		intT2.start();

		intT1.join();
		intT2.join();

		Thread refT1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
				}

				atomicStampedRef.compareAndSet(100, 101, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
				atomicStampedRef.compareAndSet(101, 100, atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
			}
		});

		Thread refT2 = new Thread(new Runnable() {
			@Override
			public void run() {
				int stamp = atomicStampedRef.getStamp();
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
				}
				boolean c3 = atomicStampedRef.compareAndSet(100, 101, stamp, stamp + 1);
				System.out.println(c3); // false
			}
		});

		refT1.start();
		refT2.start();

	}
}