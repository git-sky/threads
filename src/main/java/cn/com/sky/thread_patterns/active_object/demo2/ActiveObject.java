package cn.com.sky.thread_patterns.active_object.demo2;

import java.util.concurrent.Future;

public interface ActiveObject {

    Future<String> makeString(int count, char fillChar);

    void displayString(String string);

    void shutdown();
}