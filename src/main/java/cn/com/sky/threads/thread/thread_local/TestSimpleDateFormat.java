package cn.com.sky.threads.thread.thread_local;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TestSimpleDateFormat {

    public static void main(String[] args) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date today = new Date();

        Date tomorrow = new Date(today.getTime() + 1000 * 60 * 60 * 24);

        System.out.println("today= " + today);
        System.out.println("tomorrow= " + tomorrow);

        Thread thread1 = new Thread(new Thread1(dateFormat, today));
        thread1.start();
        Thread thread2 = new Thread(new Thread2(dateFormat, tomorrow));
        thread2.start();

    }

}

class Thread1 implements Runnable {
    private SimpleDateFormat dateFormat;
    private Date date;

    public Thread1(SimpleDateFormat dateFormat, Date date) {
        this.dateFormat = dateFormat;
        this.date = date;
    }

    public void run() {
        int i = 0;
        for (; ; ) {// 一直循环到出问题为止吧。
            String strDate = dateFormat.format(date);
            if (!"2018-04-19".equals(strDate)) {
                System.err.println("today=" + strDate);
                System.exit(0);
            }
            i++;
            System.out.println("i=" + i);
        }
    }
}

class Thread2 implements Runnable {
    private SimpleDateFormat dateFormat;
    private Date date;

    public Thread2(SimpleDateFormat dateFormat, Date date) {
        this.dateFormat = dateFormat;
        this.date = date;
    }

    public void run() {
        int j = 0;
        for (; ; ) {
            String strDate = dateFormat.format(date);
            if (!"2018-04-20".equals(strDate)) {
                System.err.println("tomorrow=" + strDate);
                System.exit(0);
            }
            j++;
            System.out.println("j=" + j);
        }
    }
}