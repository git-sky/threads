package cn.com.sky.thread_patterns.active_object.demo1;


class Proxy implements ActiveObject {

    private final SchedulerThread schedulerThread;
    private final Servant servant;

    public Proxy(SchedulerThread schedulerThread, Servant servant) {
        this.schedulerThread = schedulerThread;
        this.servant = servant;
    }

    @Override
    public Result<String> makeString(int count, char fillChar) {
        FutureResult<String> futureResult = new FutureResult<>();
        MakeStringRequest request = new MakeStringRequest(servant, futureResult, count, fillChar);
        schedulerThread.invoke(request);
        return futureResult;
    }

    @Override
    public void display(String string) {
        schedulerThread.invoke(new DisplayStringRequest(servant, string));
    }
}