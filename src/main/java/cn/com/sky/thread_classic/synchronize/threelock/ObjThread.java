package cn.com.sky.thread_classic.synchronize.threelock;

public class ObjThread extends Thread {
    LockTestClass lock;
    int i = 0;

    public ObjThread(LockTestClass lock, int i) {
        this.lock = lock;
        this.i = i;
    }

    public void run() {
        // 无锁方法
//		 lock.noSynMethod(this.getId(),this);//this是ObjThread对象
        // 对象锁方法1, 采用在方法上加锁方式
//		lock.synOnMethod();
        // 对象锁方法2，采用synchronized(this)的方式
//		lock.synInMethod();
        // 私有锁方法，采用synchronized(object)的方式
//		lock.synMethodWithObj();
        // 类锁方法，采用static synchronized increment的方式
//		 LockTestClass.increment();
//		 lock.increment2();
        lock.increment3();
    }

}