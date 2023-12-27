package cn.com.sky.thread_classic.synchronize;

/**
 * <pre>
 *
 * 注意： 当在对象上调用wait()方法时，执行该代码的线程立即放弃它在对象上的锁。
 * 然而调用notify()时，并不意味着这时线程会放弃其锁。 如果线程仍然在完成同步代码，则线程在移出之前不会放弃锁。
 * 因此，只要调用notify()并不意味着这时该锁变得可用。
 *  wait() 必须在synchronized 函数或者代码块里面。
 *
 * 注意：
 * 1.[wait(),notify()/notityAll()方法是普通对象的方法(Object超类中实现),而不是线程对象的方法]
 * 2.[wait(),notify()/notityAll()方法只能在同步方法中调用]
 */
public class TestThreadWaitNotify {

    /**
     * 计算输出其他线程锁计算的数据
     */
    public static void main(String[] args) {
        ThreadB b = new ThreadB();
        // 启动计算线程
        b.start();

        // try {
        // Thread.sleep(1000);//为了让子线程先执行。
        // } catch (InterruptedException e1) {
        // e1.printStackTrace();
        // }

        // 线程A拥有b对象上的锁。线程要调用wait()或notify()方法，该线程必须是那个对象锁的拥有者
        synchronized (b) {
            try {
                System.out.println("等待对象b完成计算。。。");
                // 当前线程A等待，并释放b对象上的锁。线程阻塞，释放对象锁。
                b.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("b对象计算的总和是：" + b.total);
        }
    }
}

/**
 * 计算1+2+3 ... +100的和
 */
class ThreadB extends Thread {
    int total;

    public void run() {
        try {
            Thread.sleep(1000);// 为了让子线程等待，让主线程先执行。
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }

        synchronized (this) {
            System.out.println("对象b开始计算。。。");
            for (int i = 0; i < 101; i++) {
                total += i;
            }
            // （完成计算了）唤醒在此对象监视器上等待的单个线程，在本例中线程A被唤醒
            notify();
        }
    }
}