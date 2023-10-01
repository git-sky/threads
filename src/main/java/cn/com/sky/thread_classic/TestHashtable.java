package cn.com.sky.thread_classic;

import java.util.*;

public class TestHashtable {

    public static void main(String[] args) {
        Hashtable hashtable = new Hashtable();
        hashtable.put("a", "b");

        Vector vector = new Vector();
        vector.add("a");
        vector.subList(1, 10);

        Collections.synchronizedList(new ArrayList<>());
        Collections.synchronizedMap(new HashMap<>());
    }
}
