package cn.com.sky.threads;

public class MyThread {

	public static void main(String[] args) {

		new Thread(new TheThread1()).start();

		for (int i = 0; i < 5; i++) {
			new Thread(new TheThread2()).start();

		}

	}

}

class TheThread1 implements Runnable {

	@Override
	public void run() {

		while (true) {

		}
	}
}

class TheThread2 implements Runnable {

	@Override
	public void run() {

		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
