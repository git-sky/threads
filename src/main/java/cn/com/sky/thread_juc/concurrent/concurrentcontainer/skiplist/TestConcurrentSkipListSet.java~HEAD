package cn.com.sky.thread_juc.concurrent.concurrentcontainer.skiplist;

import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 * 
 * 1）ConcurrentSkipListSet<E>和TreeSet一样，都是支持自然排序，并且可以在构造的时候定义Comparator<E>
 * 的比较器，该类的方法基本和TreeSet中方法一样（方法签名一样）；
 * 
 * 2）和其他的Set集合一样，ConcurrentSkipListSet<E>都是基于Map集合的，ConcurrentSkipListMap便是它的底层实现；
 * 
 * 3）在多线程的环境下，ConcurrentSkipListSet<E>中的contains、add、remove操作是安全的，多个线程可以安全地并发 执行插入、移除和访问操作。但是对于批量操作
 * addAll、removeAll、retainAll 和 containsAll并不能保证以原子方式执行，
 * 理由很简单，因为addAll、removeAll、retainAll底层调用的还是contains、add、remove的方法，在批量操作时，只能保证
 * 每一次的contains、add、remove的操作是原子性的（即在进行contains、add、remove三个操作时，不会被其他线程打断），而 不能保证每一次批量的操作都不会被其他线程打断。
 * 
 * 4）此类不允许使用 null 元素，因为无法可靠地将 null 参数及返回值与不存在的元素区分开来。
 */
public class TestConcurrentSkipListSet {

	private static ConcurrentSkipListSet<Student> listSet = new ConcurrentSkipListSet<Student>();

	static {
		for (int i = 1; i <= 30; i++) {
			listSet.add(new Student(new Long(i)));
		}
	}

	public static void main(String[] args) {

		ExecutorService threadPool = Executors.newFixedThreadPool(3);

		threadPool.execute(new Runnable() {
			public void run() {
				while (listSet.size() != 0) {
					sop(Thread.currentThread().getName() + " : " + listSet.pollFirst()); // 获取并移除第一个（最低）元素
				}
			}
		});

		threadPool.execute(new Runnable() {
			public void run() {
				while (listSet.size() != 0) {
					sop(Thread.currentThread().getName() + " : " + listSet.pollLast()); // 获取并移除最后（最高）元素
				}
			}
		});

		threadPool.execute(new Runnable() {
			public void run() {
				while (listSet.size() != 0) {
					sop(Thread.currentThread().getName() + " : " + listSet.pollFirst()); // 获取并移除第一个（最低）元素
				}
			}
		});
	}

	private static void sop(Object obj) {
		System.out.println(obj);
	}
}

class Student implements Comparable {

	private long score;

	public Student(Long score) {
		this.score = score;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

}