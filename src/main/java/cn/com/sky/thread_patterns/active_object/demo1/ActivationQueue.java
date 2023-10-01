package cn.com.sky.thread_patterns.active_object.demo1;

public class ActivationQueue {

    private static final int MAX_REQUEST_LIMIT = 100;

    private int count;
    private int tail;
    private int head;

    private MethodRequest[] requestQueue;

    public ActivationQueue() {
        this.count = 0;
        this.head = 0;
        this.tail = 0;
        requestQueue = new MethodRequest[MAX_REQUEST_LIMIT];
    }

    public synchronized void putRequest(MethodRequest request) {
        while (count >= MAX_REQUEST_LIMIT) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        requestQueue[tail] = request;
        tail = (tail + 1) % requestQueue.length;
        count++;
        notifyAll();
    }

    public synchronized MethodRequest takeRequest() {
        while (count <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        MethodRequest request = requestQueue[head];
        head = (head + 1) % requestQueue.length;
        count--;
        notifyAll();
        return request;
    }

}