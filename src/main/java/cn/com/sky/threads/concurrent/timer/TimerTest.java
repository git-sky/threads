package cn.com.sky.threads.concurrent.timer;

import java.util.Timer;
import java.util.TimerTask;

class TimerTaskTest extends TimerTask {
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("start....");
		System.out.println(System.currentTimeMillis());
	}
}

// //以下是几种调度task的方法：
// timer.schedule(task, time);
// // time为Date类型：在指定时间执行一次。
// timer.schedule(task, firstTime, period);
// // firstTime为Date类型,period为long
// // 从firstTime时刻开始，每隔period毫秒执行一次。
// timer.schedule(task, delay)
// // delay 为long类型：从现在起过delay毫秒执行一次
// timer.schedule(task, delay, period)
// // delay为long,period为long：从现在起过delay毫秒以后，每隔period
// // 毫秒执行一次。

public class TimerTest {
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerTaskTest(), 1000, 1000);

		// timer.scheduleAtFixedRate(new TimerTaskTest(), 1000,1000);
		// ###说明：该方法和schedule的相同参数的版本类似，不同的是，如果该任务因为某些原因（例如垃圾收集）而延迟执行，那么接下来的任务会尽可能的快速执行，以赶上特定的时间点。
	}
}
