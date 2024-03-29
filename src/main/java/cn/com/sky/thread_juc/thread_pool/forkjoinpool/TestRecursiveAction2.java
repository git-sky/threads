package cn.com.sky.thread_juc.thread_pool.forkjoinpool;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TestRecursiveAction2 {

    @Test
    public void run() throws InterruptedException {

        int SIZE = 10;

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        Random rnd = new Random();
        long[] array = new long[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = rnd.nextInt();
        }
        forkJoinPool.submit(new SortedAction(array));

        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1000, TimeUnit.SECONDS);

        for (int i = 1; i < SIZE; i++) {
            assertTrue(array[i - 1] < array[i]);
        }
    }
}

class SortedAction extends RecursiveAction {

    private static final long serialVersionUID = 1L;

    final long[] array;
    final int start;
    final int end;
    private int THRESHOLD = 100; // For demo only

    public SortedAction(long[] array) {
        this.array = array;
        this.start = 0;
        this.end = array.length - 1;
    }

    public SortedAction(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    protected void compute() {
        if (end - start < THRESHOLD)
            sequentiallySort(array, start, end);
        else {
            int pivot = partition(array, start, end);
            new SortedAction(array, start, pivot - 1).fork();
            new SortedAction(array, pivot + 1, end).fork();
        }
    }

    private int partition(long[] array, int start, int end) {
        long x = array[end];
        int i = start - 1;
        for (int j = start; j < end; j++) {
            if (array[j] <= x) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, end);
        return i + 1;
    }

    private void swap(long[] array, int i, int j) {
        if (i != j) {
            long temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void sequentiallySort(long[] array, int lo, int hi) {
        Arrays.sort(array, lo, hi + 1);
    }
}