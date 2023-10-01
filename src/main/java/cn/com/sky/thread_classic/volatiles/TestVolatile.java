package cn.com.sky.thread_classic.volatiles;

public class TestVolatile extends Thread {

    volatile int a;
    int b;

    public static void main(String[] args) {

        Thread t = new TestVolatile();
        t.start();

        Thread t2 = new TestVolatile();
        t2.start();

//        System.out.println(a);
//        System.out.println(b);


    }

    @Override
    public void run() {
        read();
        write();
    }


    public void read() {
        a = 1;
        b = 2;

        System.out.println(a);
        System.out.println(b);
    }

    public void write() {
        a = 3;
        b = 4;

        System.out.println(a);
        System.out.println(b);
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
