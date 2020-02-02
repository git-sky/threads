package cn.com.sky.thread_classic.thread_local;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat线程不安全，多线程执行，会出问题。
 */
public class TestSimpleDateFormatNotSafe {

    public static void main(String[] args) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date today = new Date();
        Date tomorrow = new Date(today.getTime() + 1000 * 60 * 60 * 24);

        System.out.println("today= " + today);
        System.out.println("tomorrow= " + tomorrow);

        Thread thread1 = new Thread(new TaskToday(dateFormat, today));
        thread1.start();
        Thread thread2 = new Thread(new TaskTomorrow(dateFormat, tomorrow));
        thread2.start();
    }

}

class TaskToday implements Runnable {
    private SimpleDateFormat dateFormat;
    private Date date;

    public TaskToday(SimpleDateFormat dateFormat, Date date) {
        this.dateFormat = dateFormat;
        this.date = date;
    }

    @Override
    public void run() {
        int i = 0;
        for (; ; ) {// 一直循环到出问题为止吧。
            String strDate = dateFormat.format(date);
            if (!"2019-12-12".equals(strDate)) {
                System.err.println("today=" + strDate);
                System.exit(0);
            }
            i++;
            System.out.println("i=" + i);
        }
    }
}

class TaskTomorrow implements Runnable {
    private SimpleDateFormat dateFormat;
    private Date date;

    public TaskTomorrow(SimpleDateFormat dateFormat, Date date) {
        this.dateFormat = dateFormat;
        this.date = date;
    }

    @Override
    public void run() {
        int j = 0;
        for (; ; ) {
            String strDate = dateFormat.format(date);
            if (!"2019-12-13".equals(strDate)) {
                System.err.println("tomorrow=" + strDate);
                System.exit(0);
            }
            j++;
            System.out.println("j=" + j);
        }
    }
}