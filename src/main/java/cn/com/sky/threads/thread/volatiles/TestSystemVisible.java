package cn.com.sky.threads.thread.volatiles;

/**
 *
 */
public class TestSystemVisible extends Thread {

    private boolean stop = false;
    private Object obj = new Object();

    //            System.out.println("...........");//不加volatile，写上这一句，会结束死循环。

//            synchronized (obj){
//
//            }

    public void run() {
        while (!stop) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                }
            }).start();
        }

        while (!stop) {
            synchronized (obj){}
        }
    }

    public void stopIt() {
        stop = true;
    }

    public boolean getStop() {
        return stop;
    }

    public static void main(String[] args) throws Exception {
        VisibilityTest v = new VisibilityTest();
        v.start();

        Thread.sleep(1000);
        v.stopIt();
        Thread.sleep(2000);
        System.out.println("finish main");
        System.out.println(v.getStop());
    }
}

class Th extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {

        }
    }
}
