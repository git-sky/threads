package cn.com.sky.thread_patterns.active_object.demo1;


public abstract class MethodRequest<T> {

    protected final Servant servant;
    protected final FutureResult<T> futureResult;

    protected MethodRequest(Servant servant, FutureResult<T> futureResult) {
        this.servant = servant;
        this.futureResult = futureResult;
    }

    public abstract void execute();

}