package cn.com.sky.thread_juc.concurrent.blockingqueue;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * 1.DelayQueue
 * 是一个无界的BlockingQueue，用于放置实现了Delayed接口的对象，其中的对象只能在其到期时才能从队列中取走。这种队列是有序的，即队头对象的延迟到期时间最长。
 * 注意：不能将null元素放置到这种队列中。
 *
 * 2.Delayed 一种混合风格的接口，用来标记那些应该在给定延迟时间之后执行的对象。 此接口的实现必须定义一个 compareTo 方法，该方法提供与此接口的 getDelay
 * 方法一致的排序。
 *
 * 3.DelayQueue队列中保存的是实现了Delayed接口的实现类，里面必须实现getDelay()和compareTo()方法，
 * 前者用于取DelayQueue里面的元素时判断是否到了延时时间，否则不予获取，是则获取。 compareTo()方法用于进行队列内部的排序。
 *
 * 4.使用PriorityQueue实现队列
 *
 * 5.一把锁，一个条件变量
 */
class Student implements Runnable, Delayed {
    private String name;
    private long submitTime;// 交卷时间
    private long workTime;// 考试时间（做题的时间）

    public Student() {
    }

    public Student(String name, long submitTime) {
        super();
        this.name = name;
        workTime = submitTime;
        // 都转为转为ns
        this.submitTime = TimeUnit.NANOSECONDS.convert(submitTime, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    @Override
    public void run() {
        System.out.println(name + " 交卷,用时" + workTime / 100 + "分钟");
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(submitTime - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Student that = (Student) o;
        return submitTime > that.submitTime ? 1 : (submitTime < that.submitTime ? -1 : 0);
    }

    public static class EndExam extends Student {
        private ExecutorService exec;

        public EndExam(int submitTime, ExecutorService exec) {
            super(null, submitTime);
            this.exec = exec;
        }

        @Override
        public void run() {
            System.out.println("考试时间到！关闭线程池！");
            exec.shutdownNow();//使用此方法中断线程。
        }
    }

}

class Teacher implements Runnable {

    private DelayQueue<Student> students;

    // private ExecutorService exec;

    public Teacher(DelayQueue<Student> students, ExecutorService exec) {
        super();
        this.students = students;
        // this.exec = exec;
    }

    @Override
    public void run() {
        try {
            System.out.println("考试开始……");
            while (!Thread.interrupted()) {
                students.take().run();
            }
            System.out.println("考试结束……");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

public class TestDelayQueue {

    private static final int STUDENT_SIZE = 45;

    public static void main(String[] args) {
        Random r = new Random();
        DelayQueue<Student> studentDelayQueue = new DelayQueue<>();

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < STUDENT_SIZE; i++) {
            studentDelayQueue.put(new Student("学生" + (i + 1), 3000 + r.nextInt(9000)));
        }
        studentDelayQueue.put(new Student.EndExam(12000, exec));// 1200为考试结束时间

        exec.execute(new Teacher(studentDelayQueue, exec));

    }

}