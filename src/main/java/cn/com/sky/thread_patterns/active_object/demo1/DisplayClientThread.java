package cn.com.sky.thread_patterns.active_object.demo1;

public class DisplayClientThread extends Thread {

    private final ActiveObject activeObject;

    public DisplayClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        for (int i = 0; true; i++) {
            String string = Thread.currentThread().getName() + " " + i;
            activeObject.display(string);
            ThreadUtil.sleep(20);
        }
    }

}