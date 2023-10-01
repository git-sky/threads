package cn.com.sky.thread_juc.sdf;

import java.text.ParseException;
import java.util.concurrent.CountDownLatch;

public class TestDateUtil {

    public static class TestSafe extends Thread {

        CountDownLatch cdl;

        public TestSafe(CountDownLatch cdl) {
            this.cdl = cdl;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                // try {
                // this.join(2000);
                // } catch (InterruptedException e1) {
                // e1.printStackTrace();
                // }
                try {

//                     System.out.println(this.getName() + ":" + DateUtilNotSafe.parse("2013-05-24 06:02:20"));
//                    System.out.println(date);
//                    Date date = DateUtilSafe.parse("2013-05-24 06:02:20");
//                    System.out.println(date);
//                     System.out.println(this.getName() + ":" +
//                     DateUtilSync.parse("2013-05-24 06:02:20"));
                     System.out.println(this.getName() + ":" +
                     DateUtilThreadLocal.parse("2013-05-24 06:02:20"));
                    // System.out.println(this.getName() + ":" +
                    // DateUtilThreadLocal2.parse("2013-05-24 06:02:20"));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            cdl.countDown();
        }
    }

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        System.out.println(begin);
        CountDownLatch cdl = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            new TestSafe(cdl).start();
        }
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("总耗时:" + (System.currentTimeMillis() - begin));

    }
}