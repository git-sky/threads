package cn.com.sky.thread_patterns.active_object.demo2;

public class ActiveObjectFactory {

    public static ActiveObject createActiveObject() {
        return new ActiveObjectImpl();
    }
}