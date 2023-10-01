package cn.com.sky.thread_patterns.active_object.demo1;

public class SchedulerThread extends Thread {

    private final ActivationQueue activationQueue;

    public SchedulerThread(ActivationQueue activationQueue) {
        this.activationQueue = activationQueue;
    }


    public void invoke(MethodRequest request) {
        activationQueue.putRequest(request);
    }

    @Override
    public void run() {
        while (true) {
            MethodRequest request = activationQueue.takeRequest();
            request.execute();
        }
    }
}