package cn.com.sky.thread_patterns.active_object.demo1;

public class ActiveObjectFactory {

    public static ActiveObject createActiveObject() {
        Servant servant = new Servant();
        ActivationQueue activationQueue = new ActivationQueue();
        SchedulerThread schedulerThread = new SchedulerThread(activationQueue);

        Proxy proxy = new Proxy(schedulerThread, servant);
        schedulerThread.start();
        return proxy;
    }

}