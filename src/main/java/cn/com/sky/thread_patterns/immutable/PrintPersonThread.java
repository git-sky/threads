package cn.com.sky.thread_patterns.immutable;


public class PrintPersonThread extends Thread {
    private Person person;

    public PrintPersonThread(Person person) {
        this.person = person;
    }

    /*
     * "字符串" + 实例  会自动调用实例的toString()方法
     */
    @Override
    public void run() {
        while (true) {
            System.out.println(Thread.currentThread().getName() + " prints " + person);
        }
    }
}