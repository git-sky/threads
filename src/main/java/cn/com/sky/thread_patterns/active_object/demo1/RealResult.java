package cn.com.sky.thread_patterns.active_object.demo1;

public class RealResult<T> implements Result<T> {

    private final T resultValue;

    public RealResult(T resultValue) {
        this.resultValue = resultValue;
    }

    @Override
    public T getResultValue() {
        return resultValue;
    }

}