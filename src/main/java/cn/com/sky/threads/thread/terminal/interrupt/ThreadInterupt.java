package cn.com.sky.threads.thread.terminal.interrupt;

/**
 * <pre>
 *
 *
 * AQS和Java线程池中都大量用到了中断，主要的作用是唤醒线程、取消任务和清理（如ThreadPoolExecutor的shutdown方法）。
 * AQS中的acquire方法也有中断和不可中断两种。
 *
 * 其中对于InterruptedException如何处理最重要的一个原则就是Don't swallow interrupts，一般两种方法：
 * 1、继续设置interrupted status
 * 2、抛出新的InterruptedException
 *
 * try {
 * ………
 * } catch (InterruptedException e) {
 * // Restore the interrupted status
 * Thread.currentThread().interrupt();
 * // or thow a new
 * //throw new InterruptedException();
 * }
 *
 * AQS的acquire就用到了第一种方法。
 *
 * </pre>
 */
public class ThreadInterupt {
}
