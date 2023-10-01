package cn.com.sky.threads.concurrent.test_unsafe;

import sun.misc.Unsafe;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * 使用Unsafe创建实例而不是调用构造方法。
 *
 * 调用allocateInstance函数避免了在我们不需要构造函数的时候却调用它。
 */
@SuppressWarnings("restriction")
public class TestUnsafe implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Unsafe UNSAFE;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            UNSAFE = (Unsafe) theUnsafe.get(null);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    public static void main(String[] args) {
        // new MyTest(10);

        Class<MyTest> clazz = MyTest.class;
        try {
            MyTest a = (MyTest) UNSAFE.allocateInstance(clazz);
            System.out.println(a.getNum());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    class MyTest {

        private int num;


        public MyTest(int num) {
            System.out.println("Hello Mum");
            this.num = num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getNum() {
            return num;
        }
    }
}

