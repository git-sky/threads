package cn.com.sky.thread_classic;

class Tson extends Thread {
    public int x = 0;

    @Override
    public void run() {
        System.out.println(++x);
    }
}

class Rson implements Runnable {
    private int x = 0;

    @Override
    public void run() {
        System.out.println(++x);
    }
}

public class TestThreadRT {
    public static void main(String[] args) throws Exception {

        // 10个线程对象产生的10个线程运行时打印了10次1
//		for (int i = 0; i < 10; i++) {
//			Thread t = new Tson();
//			t.start();
//		}

        // 10个线程对象产生的10个线程运行时打印了10次1
        // for (int i = 0; i < 10; i++) {
        // Thread t = new Thread(new Rson());;
        // t.start();
        // }

        // 10个线程对象产生的10个线程运行时打印了10个数(但是顺序不确定，大小不确定(因为自增和输出都没有同步
        // ))。我们把下面的10个线程称为同一实例(Runnable实例)的多个线程
        Rson r = new Rson();
        for (int i = 0; i < 10000; i++) {
            Thread t = new Thread(r);
            t.start();
        }

    }
}