package cn.com.sky.threads.thread.thread_local;

/**
 * <pre>
 * 
 * 概括起来说，对于多线程资源共享的问题，同步机制采用了“以时间换空间”的方式，而ThreadLocal采用了“以空间换时间”的方式。
 * 前者仅提供一份变量，让不同的线程排队访问， 而后者为每一个线程都提供了一份变量，因此可以同时访问而互不影响。
 * 
 * 
 * 每个线程都有这样一个map，执行ThreadLocal.get()时，各线程从自己的map中取出放进去的对象，因此取出来的是各自自己线程中的对象，ThreadLocal实例是作为map的key来使用的。
 * 
 * 试想如果不用ThreadLocal怎么来实现呢？可能就要在action中创建session，然后把session一个个传到service和dao中，这可够麻烦的。或者可以自己定义一个静态的map
 * ，将当前thread作为key，创建的session作为值，put到map中，应该也行，这也是一般人的想法，但事实上，ThreadLocal的实现刚好相反，它是在每个线程中有一个map，
 * 而将ThreadLocal实例作为key，这样每个map中的项数很少，而且当线程销毁时相应的东西也一起销毁了。
 * 
 * 
 * 总之，ThreadLocal不是用来解决对象共享访问问题的，而主要是提供了保持对象的方法和避免参数传递的方便的对象访问方式。
 * 归纳了两点：
 * 1.每个线程中都有一个自己的ThreadLocalMap类对象，可以将线程自己的对象保持到其中，各管各的，线程可以正确的访问到自己的对象。
 * 2.将一个共用的ThreadLocal静态实例作为key，将不同对象的引用保存到不同线程的ThreadLocalMap中，然后在线程执行的各处通过这个静态ThreadLocal实例的get()方法取得自己线程保存的那个对象
 * ，避免了将这个对象作为参数传递的麻烦。
 * 
 * </pre>
 */
public class TestThreadLocal {
	// 定义匿名子类创建ThreadLocal的变量
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		// 覆盖初始化方法
		@Override
		public Integer initialValue() {
			return 0;
		}
	};

	// 下一个序列号
	public int getNextNum() {
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}

	private static class TestClient extends Thread {
		private TestThreadLocal sn;

		public TestClient(TestThreadLocal sn) {
			this.sn = sn;
		}

		// 线程产生序列号
		public void run() {
			for (int i = 0; i < 300; i++) {
				System.out.println("thread[" + Thread.currentThread().getName() + "] sn[" + sn.getNextNum() + "]");
			}
		}
	}

	public static void main(String[] args) {
		TestThreadLocal sn = new TestThreadLocal();
		// 三个线程产生各自的序列号
		TestClient t1 = new TestClient(sn);
		TestClient t2 = new TestClient(sn);
		TestClient t3 = new TestClient(sn);
		t1.start();
		t2.start();
		t3.start();
	}
}
