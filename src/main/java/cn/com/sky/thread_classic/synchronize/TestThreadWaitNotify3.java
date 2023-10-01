package cn.com.sky.thread_classic.synchronize;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 * jsr133 - 14 wait 集与通知(Notification)
 *
 * 如果将中断作为第一个发生的事件，那么 t 最后会从 wait 调用中 抛出 InterruptedException 返回， wait 集中的某一其他线程 (如果在通知那一刻 wait 集中还有线程的话)就必须接受通知事件。
 * 如果通知被当作是第一个发生的事件，那么 t 最后会从 wait 中正常返回，而留下中断待处理。
 *
 * </pre>
 */
public class TestThreadWaitNotify3 {

    public static void main(String[] args) {

        final Object obj = new Object();

        List<Thread> threadList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (obj) {
                        try {
                            System.out.println("before wait:" + Thread.currentThread().getName());

                            // 1.当前线程“休眠”，等待被唤醒；2.释放obj对象上的锁。
                            // obj.wait(0);
                            obj.wait(0, 0);

                            //如果interrupted=true，说明该线程在等待的时候既被通知又被中断，而且是先收到通知，然后从wait中返回，并留下中断待处理。
                            System.out.println("awake from wait:      " + Thread.currentThread().getName() + ", interrupted=" + Thread.interrupted());
                        } catch (InterruptedException e) {
                            //抛出InterruptedException，会自动清空中断状态。
                            System.out.println("awake from interrupt: " + Thread.currentThread().getName() + ", interrupted=" + Thread.interrupted());

//                            e.printStackTrace();
//                            System.out.println(e.getMessage());
                        }
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        synchronized (obj) {

            long start = System.currentTimeMillis();

            //注意，如果一个线程既被中断又通过 notify 来唤醒，若该线程通过抛出 InterruptedException 来从 wait 中返回，那么 wait集中的某一其他线程必须响应通知事件。
            for (int i = 1; i <= 3; i++) {
                Thread t = threadList.get(i);
                t.interrupt();
                obj.notify();
                System.out.println(i);
            }


            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println((System.currentTimeMillis() - start) + "ms");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
