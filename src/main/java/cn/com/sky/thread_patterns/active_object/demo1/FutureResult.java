package cn.com.sky.thread_patterns.active_object.demo1;

public class FutureResult<T> implements Result<T> {

    private boolean isReady = false;
    private Result<T> result;

    public synchronized void setResult(Result<T> result) {
        this.result = result;
        isReady = true;
        notifyAll();
    }

    @Override
    public synchronized T getResultValue() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException e) {
                //ignore
            }
        }

        return result.getResultValue();
    }

}