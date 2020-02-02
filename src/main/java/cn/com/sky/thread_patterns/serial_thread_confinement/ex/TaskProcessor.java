package cn.com.sky.thread_patterns.serial_thread_confinement.ex;

/**
 * 对任务处理的抽象。
 *
 * @param <T> 表示任务的类型
 * @param <V> 表示任务处理结果的类型
 * @author Viscent Huang
 */
public interface TaskProcessor<T, V> {
    /**
     * 对指定任务进行处理。
     *
     * @param task 任务
     * @return 任务处理结果
     * @throws Exception
     */
    V doProcess(T task) throws Exception;
}