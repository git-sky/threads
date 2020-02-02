package cn.com.sky.thread_patterns.single_threaded_execution;


public class Gate {
    private int counter = 0;
    private String name = "NoBody";
    private String address = "NoWhere";

    public synchronized void pass(String name, String address) {
        this.counter++;
        this.name = name;
        this.address = address;
        check();
    }

    /*
     * why? toString(）方法是public， 持有该实例的“代码块”都可以进行调用， 虽然在本次逻辑测试当中去掉也没事， 但是为了完全通用Gate类，
     * 必须进行synchronized， 因为一旦在pass方法执行过程中，调用了toString()方法， 那么就造成了“脏读”， 可能会出现 ： Alice, NoWhere
     */
    @Override
    public synchronized String toString() {
        return "No." + counter + ": " + name + ", " + address;
    }

    private void check() {
        if (name.charAt(0) != address.charAt(0)) {
            System.out.println("***** BROKEN *****" + toString());
        }
    }
}
