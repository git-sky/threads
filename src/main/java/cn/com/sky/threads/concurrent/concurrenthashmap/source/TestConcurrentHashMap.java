package cn.com.sky.threads.concurrent.concurrenthashmap.source;

import java.util.ArrayList;
import java.util.Iterator;

public class TestConcurrentHashMap {

    public static void main(String[] args) {

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>(4*4, 0.75f, 4);

        map.print();

        // HashMap<String, String> map = new HashMap<String, String>();

        ArrayList<String> arr = new ArrayList<String>();

        for (int i = 0; i < 10; i++) {
            arr.add("k:" + i);
        }

        Iterator<String> it = arr.iterator();
        while (it.hasNext()) {
            String element = it.next();
            // System.out.println(element);
            map.put(element, "v:" + element);
            map.remove("abc");
            map.size();
            map.get("abc");
        }
        //		map.print();

        // String key = "key";
        // String oldValue = "oldValue";
        // String newValue = "newValue";
        //
        // map.clear();
        // map.contains(newValue);
        // map.containsKey(key);
        // map.containsValue(newValue);
        // map.elements();
        //
        // Set<Entry<String, String>> set = map.entrySet();
        // Iterator<Map.Entry<String, String>> iter = set.iterator();
        // while (iter.hasNext()) {
        // Entry<String, String> entry = iter.next();
        // entry.getKey();
        // entry.getValue();
        // }
        //
        // map.equals(new Object());
        // map.get(key);
        // map.isEmpty();
        //
        // Enumeration<String> enums = map.keys();
        // while (enums.hasMoreElements()) {
        // String element = enums.nextElement();
        // }
        //
        //
        // map.keySet();
        //
        // map.put(key, newValue);
        // map.putAll(new HashMap());
        // map.putIfAbsent(key, newValue);
        //
        // map.remove(key);
        // map.remove(key, newValue);
        //
        // map.replace(key, newValue);
        // map.replace(key, oldValue, newValue);
        //
        // map.size();
        // map.values();

        // int h = 1;
        // System.out.println(Integer.toBinaryString(h));
        // h += (h << 15) ^ 0xffffcd7d;
        // System.out.println(Integer.toBinaryString(h));
        // h ^= (h >>> 10);
        // System.out.println(Integer.toBinaryString(h));
        // h += (h << 3);
        // System.out.println(Integer.toBinaryString(h));
        // h ^= (h >>> 6);
        // System.out.println(Integer.toBinaryString(h));
        // h += (h << 2) + (h << 14);
        // System.out.println(Integer.toBinaryString(h));
        // h = h ^ (h >>> 16);
        // System.out.println(Integer.toBinaryString(h));

    }

}
