package cn.com.sky.thread_patterns.thread_per_message;

public class Helper {
    public void handle(int count, char c) {
        System.out.println("    handle begin " + c);
        for (int i = 0; i < count; i++) {
            slowly();
            System.out.print(c);
        }
        System.out.println();
        System.out.println("    handle end " + c);
    }

    private void slowly() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}