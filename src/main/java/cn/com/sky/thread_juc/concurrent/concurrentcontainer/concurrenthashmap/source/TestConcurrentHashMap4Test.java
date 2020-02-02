package cn.com.sky.thread_juc.concurrent.concurrentcontainer.concurrenthashmap.source;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

public  class TestConcurrentHashMap4Test {

    static ConcurrentHashMap<String, String> map = null;
    static List<String> list = null;
    static int NUM = 30;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        map = new ConcurrentHashMap<String, String>(2 * 2, 6f, 2);
        list = new ArrayList<String>();

        for (int i = 0; i < NUM; i++) {
            list.add(String.valueOf(i));
        }
    }

    @Test
    public void print() {
        map.print();
    }

    @Test
    public void add() {// rehash
        for (String s : list) {
            String key = "k" + s;
            String value = "v" + s;

            System.out.println("===============================================================================");
            System.out.println(key);
            map.put(key, value);
            print();
        }
    }

    // @Test
    // public void rehash() {
    // addMap();
    // print();
    //
    // // rehash:k25
    // // rehash:k51
    //
    // }

    @Test
    public void remove() {
        addMap();
        print();
        // for (String s : list) {
        // String key = "k" + s;
        // System.out.println("===============================================================================");
        // System.out.println(key);
        // map.remove(key);
        // print();
        // }

        map.remove("k" + 15);
        print();

    }

    @Test
    public void get() {
        addMap();
        print();
        for (String s : list) {
            String key = "k" + s;
            System.out.println("==============================================");
            System.out.println(key);
            map.get(key);
        }
    }

    private void addMap() {
        for (String s : list) {
            String key = "k" + s;
            String value = "v" + s;
            map.put(key, value);
        }
    }

    @Test
    public void entrySet() {

        Set<Map.Entry<String, String>> set = map.entrySet();

        Iterator<Map.Entry<String, String>> iter = set.iterator();

        while (iter.hasNext()) {
            Map.Entry<String, String> entry = iter.next();
            entry.getKey();
            entry.getValue();
        }
    }

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

}