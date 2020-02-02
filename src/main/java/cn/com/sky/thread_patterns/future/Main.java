package cn.com.sky.thread_patterns.future;


/**
 * Future模式
 *
 * <pre>
 *
 * 适用的情况
 * 当一个线程向其他线程委托了处理, 然后还想要得到处理结果时.
 *
 * 实现的方式
 * 编写一个与处理结果具有相同接口的Future类, 在处理的开始立马返回该Future对象, 然后等到被委托的线程处理完毕,再将处理结果设置到Future对象中.这样委托处理的线程就可以在自己觉得合适的时机去获取处理结果, 而不用一直阻塞到结果完成.
 *
 * 相关的模式
 * 在委托的方法等待处理结果的部分可以使用Guarded Suspension模式.
 * 要想在Thread-Per-Message模式中想要获取处理结果的时候可以使用Future模式.
 * 同上, 要想在Worker-Thread模式中获取处理结果也可以使用Future模式.
 *
 * </pre>
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("Main Begin");
        Host host = new Host();
        Data data1 = host.request(10, 'A');
        Data data2 = host.request(20, 'B');
        Data data3 = host.request(30, 'C');

        System.out.println("Main otherJob BEGIN");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main otherJob END");

        System.out.println("Data 1=" + data1.getContent());
        System.out.println("Data 2=" + data2.getContent());
        System.out.println("Data 3=" + data3.getContent());

        System.out.println("Main End");
    }
}
