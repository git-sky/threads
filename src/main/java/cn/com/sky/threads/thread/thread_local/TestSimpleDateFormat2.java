package cn.com.sky.threads.thread.thread_local;

import java.util.Date;

public class TestSimpleDateFormat2 {

    public static void main(String[] args) {

        String pattern = "yyyy-MM-dd";

        Date today = new Date();

        Date tomorrow = new Date(today.getTime() + 1000 * 60 * 60 * 24);

        System.out.println("today= " + today);
        System.out.println("tomorrow= " + tomorrow);

        Thread t1 = new Thread(new ThreadA(pattern, today));
        t1.start();
        Thread t2 = new Thread(new ThreadB(pattern, tomorrow));
        t2.start();

    }

}

class ThreadA implements Runnable {
    private String pattern;
    private Date date;

    public ThreadA(String pattern, Date date) {
        this.pattern = pattern;
        this.date = date;
    }

    public void run() {
        int i = 0;
        for (; ; ) {// 一直循环到出问题为止吧。
            String strDate = DateFormatUtils.getDateFormat(pattern).format(date);
            if (!"2018-04-19".equals(strDate)) {
                System.err.println("today=" + strDate);
                System.exit(0);
            }
            i++;
            System.out.println("i=" + i);
        }
    }
}

class ThreadB implements Runnable {
    private String pattern;
    private Date date;

    public ThreadB(String pattern, Date date) {
        this.pattern = pattern;
        this.date = date;
    }

    public void run() {
        int j = 0;
        for (; ; ) {
            String strDate = DateFormatUtils.getDateFormat(pattern).format(date);
            if (!"2018-04-20".equals(strDate)) {
                System.err.println("tomorrow=" + strDate);
                System.exit(0);
            }
            j++;
            System.out.println("j=" + j);
        }
    }
}