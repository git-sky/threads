package cn.com.sky.thread_patterns.immutable;


/**
 * <pre>
 *
 *
 * 通过final关键字， 确保了只能在字段初始化或类的构建方法中能够对final字段进行赋值操作
 * * 而且java保证类的构造方法是一个线程安全的， 所以在实例初始化后， 需要多线程进行操作的字段不可变的
 * * 从而确保了多线程的安全性
 * * String 类经过实例初始化后也是不可变的！
 *
 * Notice:
 * java字段保存的都是对象的引用, (除了java的内置类型), 如果该引用的地址被其他对象获取, 就可以对该地址保存的对象进行修改了, 那么也就是说, Immutable模式保证的只能是字段引用的地址不发生变化, 如果引用的地址保存的对象是可变的, 那么一定要小心对待, 否则就会发生我们意想不到的破坏.
 * 我们这里的例子是没有问题的, 因为String类就是Immutable的.
 * 这里强调一个问题, 在构造方法中,一定要小心"this逃逸", 否则在实例化过程中, 其他线程是可以访问this实例的,但这个时候并未初始化完成!
 *
 * </pre>
 */
public class Person {
    private final String name;
    private final String address;

    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "[ Person: name = " + name + ", address = " + address + " ]";
    }
}