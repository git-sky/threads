package cn.com.sky.thread_patterns.active_object.demo2;


/**
 * Active Object模式
 */
public class Main {

    public static void main(String[] args) {
        ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
        try {
            new MakerClientThread("alice", activeObject).start();
            new MakerClientThread("bobby", activeObject).start();
            new DisplayClientThread("chris", activeObject).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
