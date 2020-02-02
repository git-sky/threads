package cn.com.sky.thread_patterns.active_object.demo2;

public class DisplayClientThread extends Thread {

    private final ActiveObject activeObject;

    public DisplayClientThread(String name, ActiveObject activeObject) {
        super(name);
        this.activeObject = activeObject;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; true; i++) {
                String s = Thread.currentThread().getName() + " " + i;
                activeObject.displayString(s);
                Thread.sleep(200);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + ":" + e);
        }
    }
}