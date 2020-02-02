package cn.com.sky.thread_patterns.balking;

/**
 * Balking 模式
 *
 * <pre>
 *
 * 适用的情况
 * 不想让各个线程随意的访问实例, 保持实例的安全性, 但是又想在保证安全性同时提高响应速度.
 *
 * 实现的方式
 * 如果实例处于非正确的状态, 不等待实例恢复正确, 而是直接返回.
 * 判断检测实例的状态是不正确的.
 *  通过return语句返回.
 *  通过throw抛出一个异常中断执行.
 *
 * 相关的模式
 * 如果线程需要等待正常的数据返回, 那么可以使用Guarded Suspension模式.
 *
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        Data data = new Data("balkingTest", "this is first");
        new ChangerThread("changer-1", data).start();
        new SaverThread("saver-1", data).start();
    }
}