package cn.com.sky.thread_patterns.active_object.demo1;


/**
 * Active Object 模式
 *
 * <pre>
 *
 * 别名
 * Actor
 * Concurrent Object
 * 适用的情况
 * actor是一个很抽象多线程模式, 每一个actor是线程独立并且有属于自己的状态, 多个actor互相发送消息以完成最终的任务. 你可以将actor模式理解为一个团队, 这个团队里面的个人就是一个运行在独立线程上的个体, 由他们互相交流并且单独处理自己的工作, 最后完成任务.
 * 关于actor模式, 这里有一篇个人觉得很好的文章(英文)--The actor model in 10 minutes
 *
 * 实现的方式
 * 构造一个ActiveObject类, 来充当actor角色, 有actor角色去完成任务, 并且完成交互.
 *
 * 相关的模式
 * 实现actor角色会用到Worker Thread模式
 * 将消息发送给actor时候,将消息放入队列, actor从队列中取消息进行处理, 用到了Producer-Consumer模式.
 * 将处理结果返回给最终调用方需要用到Future模式.
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        new MakerClientThread("Alice", activeObject).start();
        new MakerClientThread("Bobby", activeObject).start();
        new DisplayClientThread("Chris", activeObject).start();
    }

}