package cn.com.sky.threads.concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * RecursiveTask ：用于有返回结果的任务。
 * 
 * 斐波那契
 */
public class TestRecursiveTask2 extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;

	final int n;

	TestRecursiveTask2(int n) {
		this.n = n;
	}

	private int compute(int small) {
		final int[] results = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };
		return results[small];
	}

	public Integer compute() {
		if (n <= 10) {
			return compute(n);
		}
		TestRecursiveTask2 f1 = new TestRecursiveTask2(n - 1);
		f1.fork();
		TestRecursiveTask2 f2 = new TestRecursiveTask2(n - 2);
		return f2.compute() + f1.join();
	}

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ForkJoinTask<Integer> fjt = new TestRecursiveTask2(40);
		ForkJoinPool fjpool = new ForkJoinPool();
		fjpool.execute(fjt);
		Integer resultInt = fjt.get();
		System.out.println(resultInt);
	}
}
