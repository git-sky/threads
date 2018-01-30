package cn.com.sky.threads.thread.synchronize.threelock;

/**
 * <pre>
 * 
 * 一、相关约定
 * 
 * 为了明确后文的描述，先对本文涉及到的锁的相关定义作如下约定：
 * 
 * 1.类锁：在代码中的方法上加了static和synchronized的锁，或者synchronized(xxx.class）的代码段，如下文中的increament()。
 * 
 * 2.对象锁：在代码中的方法上加了synchronized的锁，或者synchronized(this）的代码段，如下文中的synOnMethod()和synInMethod()；
 * 这种对象锁锁定的是当前对象本身。
 * 
 * 3.私有锁：在类内部声明一个私有属性如private Object lock，在需要加锁的代码段synchronized(lock），如下文中的synMethodWithObj()；
 * 这也是一个对象锁，只不过锁定的是某个指定的对象。
 * 
 * 
 * 结论：类锁和对象锁不会产生竞争，二者的加锁方法不会相互影响。
 * 
 * 结论：私有锁和对象锁也不会产生竞争，二者的加锁方法不会相互影响(因为锁的不是同一个对象)。
 * 
 * 结论：synchronized直接加在方法上和synchronized(this)都是对当前对象加锁，二者的加锁方法够成了竞争关系，同一时刻只能有一个方法能执行。
 * 
 *
 */
public class ObjectLock {
	public static void main(String[] args) {
		// System.out.println("start time = " + System.currentTimeMillis()+"ms");
//		LockTestClass test = new LockTestClass();
		for (int i = 0; i < 5; i++) {
            LockTestClass test = new LockTestClass();
			Thread thread = new ObjThread(test, i);
			thread.start();
		}
	}
}