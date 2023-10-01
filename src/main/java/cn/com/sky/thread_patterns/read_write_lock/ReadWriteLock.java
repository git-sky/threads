package cn.com.sky.thread_patterns.read_write_lock;


public final class ReadWriteLock {
    private int readingReaders = 0;
    private int waitingWriters = 0;
    private int writingWriters = 0;
    private boolean preferWriter = true;

    /*
     * 如果按照这样来写.
     *while (writingWriters > 0) {
     *     wait();
     *   }
     *读进程多于写入进程, 并且读进程是没有互斥处理的, 也就是在一开始, 读进程就在占用住table,
     *即使有一个进程在读, 写进程就进不来
     *
     */
    public synchronized void readLock() throws InterruptedException {
        while (writingWriters > 0 || (waitingWriters > 0 && preferWriter)) {
            wait();
        }
        notifyAll();
    }

    public synchronized void readUnlock() {
        readingReaders--;
        preferWriter = true;
        notifyAll();
    }

    //finally 确保
    public synchronized void writeLock() throws InterruptedException {
        waitingWriters++;
        try {
            while (readingReaders > 0 || writingWriters > 0) {
                wait();
            }
        } finally {
            waitingWriters--;
        }
        writingWriters++;
    }

    public synchronized void writeUnlock() {
        writingWriters--;
        preferWriter = false;
        notifyAll();
    }
}