package cn.com.sky.thread_juc.thread_pool.forkjoinpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask ：用于有返回结果的任务。
 * <p>
 * 斐波那契
 */
public class TestRecursiveTaskForFabnacci extends RecursiveTask<Integer> {

    private static final long serialVersionUID = 1L;

    final int n;

    TestRecursiveTaskForFabnacci(int n) {
        this.n = n;
    }

    private int compute(int small) {
        final int[] results = {1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89};
        return results[small];
    }

    @Override
    public Integer compute() {
        if (n <= 10) {
            return compute(n);
        }
        TestRecursiveTaskForFabnacci f1 = new TestRecursiveTaskForFabnacci(n - 1);
        f1.fork();
        TestRecursiveTaskForFabnacci f2 = new TestRecursiveTaskForFabnacci(n - 2);
        return f2.compute() + f1.join();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinTask<Integer> task = new TestRecursiveTaskForFabnacci(40);

        pool.execute(task);


        Integer resultInt = task.get();

        System.out.println(resultInt);
    }
}
