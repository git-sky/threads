package cn.com.sky.threads.concurrent.callablefuture;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * <pre>
 * 
 * 下面的例子模拟一个会计算账的过程，主线程已经获得其他帐户的总额了，为了不让主线程等待 PrivateAccount类的计算结果的返回而启用新的线程去处理，
 * 并使用FutureTask对象来监控，这样，主线程还可以继续做其他事情， 最后需要计算总额的时候再尝试去获得privateAccount 的信息。
 * 
 * </pre>
 */
public class TestFutureTask3 {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {

		// 初始化一个Callable对象和FutureTask对象
		Callable pAccount = new PrivateAccount();
		
		FutureTask futureTask = new FutureTask(pAccount);
		
		// 使用futureTask创建一个线程
		Thread pAccountThread = new Thread(futureTask);
		System.out.println("futureTask线程现在开始启动，启动时间为：" + System.nanoTime());
		pAccountThread.start();
		System.out.println("主线程开始执行其他任务");

		// 从其他账户获取总金额
		int totalMoney = new Random().nextInt(100000);
		System.out.println("现在你在其他账户中的总金额为" + totalMoney);
		System.out.println("等待私有账户总金额统计完毕...");

		// 测试后台的计算线程是否完成，如果未完成则等待
		while (!futureTask.isDone()) {
			try {
				Thread.sleep(500);
				System.out.println("私有账户计算未完成继续等待...");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("futureTask线程计算完毕，此时时间为" + System.nanoTime());

		Integer privateAccountMoney = null;
		try {
			privateAccountMoney = (Integer) futureTask.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("您现在的总金额为：" + totalMoney + privateAccountMoney.intValue());
	}
}

class PrivateAccount implements Callable<Object> {
	
	Integer totalMoney;

	@Override
	public Object call() throws Exception {
		Thread.sleep(5000);
		totalMoney = new Integer(new Random().nextInt(10000));
		System.out.println("您当前有" + totalMoney + "在您的私有账户中");
		return totalMoney;
	}

}