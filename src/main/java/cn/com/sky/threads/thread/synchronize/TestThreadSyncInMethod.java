package cn.com.sky.threads.thread.synchronize;

/**
 * <pre>
 * 
 * 非static的synchronized方法和synchronized(this)用的是一个锁。
 * 
 * 6个线程持有同一个User对象锁。
 * 
 * 同步代码块
 *
 */
public class TestThreadSyncInMethod {

	public static void main(String[] args) {
		UserB u = new UserB("张三", 100);
		MyThreadB t1 = new MyThreadB("线程A", u, 20);
		MyThreadB t2 = new MyThreadB("线程B", u, -60);
		MyThreadB t3 = new MyThreadB("线程C", u, -80);
		MyThreadB t4 = new MyThreadB("线程D", u, -30);
		MyThreadB t5 = new MyThreadB("线程E", u, 32);
		MyThreadB t6 = new MyThreadB("线程F", u, 21);

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
	}
}

class MyThreadB extends Thread {
	private UserB u;
	private int y = 0;

	MyThreadB(String name, UserB u, int y) {
		super(name);
		this.u = u;
		this.y = y;
	}

	public void run() {
		u.oper(y);
	}
}

class UserB {
	private String code;
	private int cash;

	UserB(String code, int cash) {
		this.code = code;
		this.cash = cash;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void oper(int x) {
		try {
			Thread.sleep(10L);
			synchronized (this) {
				this.cash += x;
				System.out.println(Thread.currentThread().getName() + "运行结束，增加“" + x + "”，当前用户账户余额为：" + cash);
			}
			Thread.sleep(10L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "UserB{" + "code='" + code + '\'' + ", cash=" + cash + '}';
	}
}