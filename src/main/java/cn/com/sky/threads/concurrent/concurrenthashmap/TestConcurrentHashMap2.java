package cn.com.sky.threads.concurrent.concurrenthashmap;


import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap2 {

    public static void main(String[] args) {
        ConcurrentHashMap map = new ConcurrentHashMap();
        map.put("k", "v");

        map.remove("k");
    }

}
