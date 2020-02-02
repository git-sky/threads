package cn.com.sky.thread_patterns.thread_per_message;

public class Host {

    private final Helper helper = new Helper();

    public Host() {
    }

    public void request(final int count, final char c) {
        System.out.println("request begin " + count + " " + c);

        //  每个request 委托新的线程去执行
        // 适合如果处理的时间特别长, 而且对操作顺序和返回值没有要求
        new Thread() {
            @Override
            public void run() {
                helper.handle(count, c);
            }
        }.start();
        System.out.println("request end " + count + " " + c);
    }
}