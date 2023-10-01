package cn.com.sky.thread_patterns.active_object.demo1;


public class MakerClientThread extends Thread {

    private final ActiveObject activeObject;
    private final char fillChar;

    public MakerClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
        this.fillChar = name.charAt(0);
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            Result<String> result = activeObject.makeString(i, fillChar);
            ThreadUtil.sleep(10);
            String value = result.getResultValue();
            System.out.println(Thread.currentThread().getName() + ": value=" + value);
        }
    }

}