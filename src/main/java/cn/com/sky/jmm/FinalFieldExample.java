package cn.com.sky.jmm;

/**
 * <pre>
 * final字段和普通字段的差别
 *
 * 一个线程可能会执行 writer()方法，另一个线程可能会执行 reader()方法。
 * 因为 writer()方法在对象构造器执行结束之后才给 f 赋值，所以可以保证 reader()方法能看到 f.x 正确初始化的值: 即会读到 3 。
 * 然而，f.y 不 是 final 的; 因此不能保证 reader()方法能看到 4.
 *
 * </pre>
 */
public class FinalFieldExample {

    final int x;
    int y;

    static FinalFieldExample f;

    public FinalFieldExample() {
        x = 3;
        y = 4;
    }

    static void writer() {
        f = new FinalFieldExample();
    }

    static void reader() {
        if (f != null) {
            int i = f.x; // guaranteed to see 3
            int j = f.y; // could see 0
            System.out.println("i=" + i);
            System.out.println("j=" + j);
        }
    }

    public static void main(String[] args) {
//        for (int i = 0; i < 1000000; i++) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    reader();
//                }
//            }).start();
//
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    writer();
//                }
//            }).start();
//        }

        String s = "abc";
        System.out.println(s.substring(2));
    }
}