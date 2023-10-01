package cn.com.sky.thread_juc.concurrent.blockingqueue;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * <pre>
 *
 *     drainTo():一次性从BlockingQueue获取所有可用的数据对象（还可以指定获取数据的个数），
 * 　　　　通过该方法，可以提升获取数据效率；不需要多次分批加锁或释放锁。
 *
 * </pre>
 */
public class TestBlockingQueueDrainTo {
    /**
     * 实例化一个队列，队列中的容量为10
     */
    private static BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(10);

    public static void main(String[] args) {
        ScheduledExecutorService product = Executors.newScheduledThreadPool(1);
        Random random = new Random();
        product.scheduleAtFixedRate(() -> {
            int value = random.nextInt(101);
            try {
                boolean isSuccess = blockingQueue.offer(value);  //offer()方法就是网队列的尾部设置值
                System.out.println(isSuccess);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 0, 200, TimeUnit.MILLISECONDS);  //每100毫秒执行线程

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(2000);
                    System.out.println("开始取值");
                    List<Integer> list = new LinkedList<>();
                    blockingQueue.drainTo(list);  //drainTo()将队列中的值全部从队列中移除，并赋值给对应集合
                    list.forEach(System.out::println);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}