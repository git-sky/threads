package cn.com.sky.thread_classic.terminal;

public class TestException {

    private final Integer a = 1;

    public void set() {

        synchronized (a) {
            System.out.println("abc");
        }

    }

    public void get() {

        synchronized (a) {
            System.out.println("xxxx");
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {

            new TestException().set();
        }
    }


}
