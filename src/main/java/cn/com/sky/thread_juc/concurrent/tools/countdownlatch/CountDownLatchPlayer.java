package cn.com.sky.thread_juc.concurrent.tools.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 对于每位运动员，CountDownLatch减1后即结束比赛，对于整个比赛，所有运动员结束后才算结束
 */
public class CountDownLatchPlayer {

    private static final int PLAYER_AMOUNT = 5;

    public static void main(String[] args) {

        // 控制开始比赛标志
        CountDownLatch begin = new CountDownLatch(1);
        // 控制结束比赛标志
        CountDownLatch end = new CountDownLatch(PLAYER_AMOUNT);

        Player[] plays = new Player[PLAYER_AMOUNT];

        for (int i = 0; i < PLAYER_AMOUNT; i++) {
            plays[i] = new Player(i + 1, begin, end);
        }


        ExecutorService executorService = Executors.newFixedThreadPool(PLAYER_AMOUNT);
        for (Player p : plays) {
            executorService.execute(p);
        }
        System.out.println("Race begins!");
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        begin.countDown();// 开始比赛
        try {
            end.await(); // 等待end状态变为0，即为比赛结束
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Race ends!");
        }
        executorService.shutdown();
    }

    /**
     * <pre>
     *
     * 使用CountDownLatch进行异步转同步操作，每个线程退出前必须调用countDown方法，线程执行代码注意catch异常，
     * 确保countDown方法可以执行，避免主线程无法执行至countDown方法，直到超时才返回结果。
     * 说明：注意，子线程抛出异常堆栈，不能在主线程try-catch到。
     *
     * </pre>
     */
    public static class Player implements Runnable {

        private int id;
        private CountDownLatch begin;
        private CountDownLatch end;

        public Player(int i, CountDownLatch begin, CountDownLatch end) {
            super();
            this.id = i;
            this.begin = begin;
            this.end = end;
        }

        @Override
        public void run() {
            try {
                begin.await(); // 等待begin的状态为0，即等待开始比赛。
                Thread.sleep((long) (Math.random() * 100)); // 随机分配时间，即运动员完成时间
                System.out.println("Play" + id + " arrived.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                end.countDown(); // 使end状态减1，最终减至0
            }
        }
    }
}