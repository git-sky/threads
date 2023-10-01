//package cn.com.sky.jmm;
//
///**
// *
// */
//public class FinalExample {
//    int i;                            //普通变量
//    final int j;                      //final变量
//    static FinalExample obj;
//
//    public void FinalExample() {     //构造函数
//        i = 1;                        //写普通域
//        j = 2;                        //写final域
//    }
//
//    public static void writer() {    //写线程A执行
//        obj = new FinalExample();
//    }
//
//    public static void reader() {       //读线程B执行
//        FinalExample object = obj;       //读对象引用
//        int a = object.i;                //读普通域
//        int b = object.j;                //读final域
//        System.out.println(a);
//        System.out.println(b);
//    }
//
//    public static void main(String[] args) {
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FinalExample.writer();
//            }
//        }).start();
//
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        FinalExample.reader();
//                    }
//                }
//        ).start();
//    }
//}