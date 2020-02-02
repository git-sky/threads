package cn.com.sky.thread_patterns.active_object.demo1;

public class DisplayStringRequest extends MethodRequest<Object> {

    private final String string;

    public DisplayStringRequest(Servant servant, String string) {
        super(servant, null);
        this.string = string;
    }

    @Override
    public void execute() {
        servant.display(string);
    }

}