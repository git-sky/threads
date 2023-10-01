package cn.com.sky.thread_classic;

class TestThreadShare implements Runnable {

    public synchronized void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + "i=" + i);
        }
    }
}

public class TestThreadRunnable {

    public static void main(String[] args) {

        // 每次都是用同一个TestThreadShare对象来产生Thread对象的，所以产生出来的Thread对象是同一个对象的线程，所以必须实现run()函数的同步。
        TestThreadShare my = new TestThreadShare();
        new Thread(my, "1号窗口").start();
        new Thread(my, "2号窗口").start();
        new Thread(my, "3号窗口").start();

//		// 每次都是用一个新的TestThreadShare对象来产生Thread对象的，所以产生出来的Thread对象是不同对象的线程，所以所有Thread对象都可同时访问run()函数，没必要同步。
//		 TestThreadShare my1 = new TestThreadShare();
//		 TestThreadShare my2 = new TestThreadShare();
//		 TestThreadShare my3 = new TestThreadShare();
//		 new Thread(my1, "1号窗口").start();
//		 new Thread(my2, "2号窗口").start();
//		 new Thread(my3, "3号窗口").start();

    }
}