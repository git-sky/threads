package cn.com.sky.threads.concurrent.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

import org.junit.Test;

/**
 * RecursiveTask ：用于有返回结果的任务。
 */
public class TestRecursiveTask {
	@Test
	public void testDemo1() throws InterruptedException, ExecutionException {

		ForkJoinPool forkJoinPool = new ForkJoinPool();// 对线程池的扩展
		Future<Integer> result = forkJoinPool.submit(new Calculator(1, 10000));
		System.out.println(result.get());
		forkJoinPool.shutdown();
	}
}

class Calculator extends RecursiveTask<Integer> {
	private static final long serialVersionUID = 2248477153584689616L;

	private int start;
	private int end;

	public Calculator(int start, int end) {
		this.start = start;
		this.end = end;
	}

	// 计算
	@Override
	protected Integer compute() {
		int sum = 0;
		if (end - start < 100) {
			for (int i = start; i < end; i++) {
				sum += i;
			}
		} else {// 间隔有100则拆分多个任务计算
			int middle = (start + end) / 2;
			Calculator left = new Calculator(start, middle);
			Calculator right = new Calculator(middle + 1, end);
			left.fork();
			right.fork();

			sum = left.join() + right.join();
		}
		return sum;
	}
}