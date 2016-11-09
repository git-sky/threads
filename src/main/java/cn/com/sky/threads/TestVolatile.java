package cn.com.sky.threads;

public class TestVolatile extends Thread{

	volatile int a;
	int b;

	public static void main(String[] args) {
		new TestVolatile().start();
		new TestVolatile().start();
		
	}

	@Override
	public void run() {
		read();
		write();
	}
	
	
	public void read() {
		a = 1;
		b = 2;
	}

	public void write() {
		a = 3;
		b = 4;
	}

	public synchronized void syncMethod() {
		a = 5;
		b = 6;
	}

	public void syncThis() {
		synchronized (this) {
			a = 7;
			b = 8;
		}
	}
}
