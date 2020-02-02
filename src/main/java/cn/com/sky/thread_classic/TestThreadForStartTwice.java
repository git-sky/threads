package cn.com.sky.thread_classic;

public class TestThreadForStartTwice {

    public static void main(String[] args) {

        Thread t = new Thread();
        t.start();
        t.start();//线程不能启动两次,java.lang.IllegalThreadStateException
    }

}
