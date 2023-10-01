package cn.com.sky.thread_classic.synchronize;

/**
 * 6个线程持有同一个User对象锁。
 * <p>
 * 同步方法
 */
public class TestThreadSyncOnMethod {

    public static void main(String[] args) {
        User u = new User("张三", 100);
        MyThreadT t1 = new MyThreadT("线程A", u, 20);
        MyThreadT t2 = new MyThreadT("线程B", u, -60);
        MyThreadT t3 = new MyThreadT("线程C", u, -80);
        MyThreadT t4 = new MyThreadT("线程D", u, -30);
        MyThreadT t5 = new MyThreadT("线程E", u, 32);
        MyThreadT t6 = new MyThreadT("线程F", u, 21);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
    }
}

class MyThreadT extends Thread {
    private User u;
    private int y = 0;

    MyThreadT(String name, User u, int y) {
        super(name);
        this.u = u;
        this.y = y;
    }

    public void run() {
        u.oper(y);
    }
}

class User {
    private String code;
    private int cash;

    User(String code, int cash) {
        this.code = code;
        this.cash = cash;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public synchronized void oper(int x) {
        try {
            Thread.sleep(10L);
            this.cash += x;
            System.out.println(Thread.currentThread().getName() + "运行结束，增加“" + x + "”，当前用户账户余额为：" + cash);
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "User{" + "code='" + code + '\'' + ", cash=" + cash + '}';
    }
}