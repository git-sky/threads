package cn.com.sky.thread_juc.concurrent.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 通过这种方式获取unsafe实例。
 * <p>
 * 实例化sun.misc.Unsafe
 * 如果你尝试创建Unsafe类的实例，基于以下两种原因是不被允许的。
 * 1）、Unsafe类的构造函数是私有的；
 * 2）、虽然它有静态的getUnsafe()方法，但是如果你尝试调用Unsafe.getUnsafe()，会得到一个SecutiryException。这个类只有被JDK信任的类实例化。
 * 但是这总会是有变通的解决办法的，一个简单的方式就是使用反射进行实例化：
 */
@SuppressWarnings("restriction")
public class UnsafeUtil {

    private static Unsafe unsafe;

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (Exception e) {
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }

}