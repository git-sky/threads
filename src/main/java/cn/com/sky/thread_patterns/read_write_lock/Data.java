package cn.com.sky.thread_patterns.read_write_lock;


public class Data {
    private final char[] buffers;
    private final ReadWriteLock lock = new ReadWriteLock();

    public Data(int size) {
        this.buffers = new char[size];
        for (int i = 0; i < buffers.length; i++) {
            buffers[i] = '*';
        }
    }

    public char[] read() throws InterruptedException {
        lock.readLock();
        try {
            return doRead();
        } finally {
            lock.readUnlock();
        }
    }

    public void write(char c) throws InterruptedException {
        lock.writeLock();
        try {
            doWrite(c);
        } finally {
            lock.writeUnlock();
        }
    }

    private void doWrite(char c) {
        for (int i = 0; i < buffers.length; i++) {
            buffers[i] = c;
            slowly();
        }
    }


    private char[] doRead() {
        char[] newbuf = new char[buffers.length];
        for (int i = 0; i < buffers.length; i++) {
            newbuf[i] = buffers[i];
        }
        slowly();
        return newbuf;
    }

    private void slowly() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}