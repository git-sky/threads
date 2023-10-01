package cn.com.sky.thread_patterns.thread_specific_storage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class TSLog {
    private PrintWriter printWriter = null;

    public TSLog(String fileName) {
        try {
            printWriter = new PrintWriter(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void println(String s) {
        printWriter.write(s);
    }

    public void close() {
        printWriter.write("===  END OF Log  ===");
        printWriter.close();
    }
}