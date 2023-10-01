package cn.com.sky.thread_juc.concurrent.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

/**
 * 创建对象的几种方式：
 */
@SuppressWarnings("restriction")
public class TestCreateInstance {
    public static void main(String[] args) {
        // testNewObject();
        // testNewInstance();
        // testConstructor();
        // testConstructorWityParameterTypes();
        testUnsafeAllocateInstance();
    }

    public static void testUnsafeAllocateInstance() {
        Unsafe unsafe = UnsafeUtil.getUnsafe();

        try {
            DemoClass obj = (DemoClass) unsafe.allocateInstance(DemoClass.class);
            System.out.println(obj.getValue1());
            System.out.println(obj.getValue2());
            obj.setValue1(1);
            obj.setValue2(2);
            System.out.println(obj.getValue1());
            System.out.println(obj.getValue2());
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public static void testNewObject() {
        DemoClass obj = new DemoClass(1, 2);
        System.out.println(obj.getValue1());
        System.out.println(obj.getValue2());
    }

    public static void testNewInstance() {
        try {
            DemoClass obj = DemoClass.class.newInstance();// 必须提供无参的构造函数
            System.out.println(obj.getValue1());
            System.out.println(obj.getValue2());
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void testConstructor() {
        try {
            Class[] cls = new Class[]{int.class, int.class};
            Constructor c = DemoClass.class.getDeclaredConstructor(cls);
            DemoClass obj = (DemoClass) c.newInstance(1, 2);
            System.out.println(obj.getValue1());
            System.out.println(obj.getValue2());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testConstructorWityParameterTypes() {
        try {
            Constructor[] c = DemoClass.class.getDeclaredConstructors();
            Type[] parameterTypes = c[0].getGenericParameterTypes();
            // 判断type类型，依次设置默认值
            DemoClass obj = (DemoClass) c[0].newInstance(1, 2);
            System.out.println(obj.getValue1());
            System.out.println(obj.getValue2());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
