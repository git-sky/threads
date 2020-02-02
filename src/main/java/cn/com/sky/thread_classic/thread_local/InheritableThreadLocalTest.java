package cn.com.sky.thread_classic.thread_local;

public class InheritableThreadLocalTest {

    private static InheritableThreadLocal<Integer> threadLocal = new InheritableThreadLocal<Integer>();

//    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();


    public static void main(String[] args) throws InterruptedException {

        threadLocal.set(23);

        System.out.println(Thread.currentThread() + " , main value=" + threadLocal.get());

        Thread t1 = new Thread("first") {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + " , first value=" + threadLocal.get());
                threadLocal.set(28);
                System.out.println(Thread.currentThread() + " , first value =" + threadLocal.get());

                Thread t2 = new Thread("second") {
                    @Override
                    public void run() {
                        System.out.println(Thread.currentThread() + " ,second Value=" + threadLocal.get());
                        threadLocal.set(33);
                        System.out.println(Thread.currentThread() + " ,second Value=" + threadLocal.get());
                    }

                };
                t2.start();
            }
        };

        t1.start();

        Thread.sleep(1000);

        System.out.println(Thread.currentThread() + " , main value=" + threadLocal.get());

    }
}
