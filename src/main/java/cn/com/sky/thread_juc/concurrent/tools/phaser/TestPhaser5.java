package cn.com.sky.thread_juc.concurrent.tools.phaser;

import java.util.concurrent.Phaser;

public class TestPhaser5 {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(3){
            /**
             * registeredParties:线程注册的数量
             * phase:进入该方法的线程数，
             */
             protected boolean onAdvance(int phase, int registeredParties) { 
                 System.out.println("执行onAdvance方法.....，phase=" + phase + "，registeredParties=" + registeredParties);
                 return phase == 3; 
             }
        };
        
        for(int i = 0 ; i < 3 ; i++){
            Task task = new Task(phaser);
            Thread thread = new Thread(task,"task_" + i);
            thread.start();
        }
        while(!phaser.isTerminated()){
            phaser.arriveAndAwaitAdvance();    //主线程一直等待
        }
        System.out.println("主线程任务已经结束....");
    }
    
    static class Task implements Runnable{
        private final Phaser phaser;
        
        public Task(Phaser phaser){
            this.phaser = phaser;
        }
        
        @Override
        public void run() {
            do{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始执行任务...");
                phaser.arriveAndAwaitAdvance();
            }while(!phaser.isTerminated());
        }
    }
}