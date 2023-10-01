package cn.com.sky.thread_juc.thread_pool.forkjoinpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *
 * 在Java7中，JDK提供对多线程开发提供了一个非常强大的框架，就是Fork/Join框架。
 *
 * Work-stealing策略
 * 指的是:
 * 当一个线程正在等待他创建的子线程运行的时候，当前线程如果完成了自己的任务后，就会寻找还没有被运行的任务并且运行他们，
 * 这就是和Executors这个方式最大的区别，更加有效的使用了线程的资源和功能。所以非常推荐使用Fork/Join框架。
 *
 * RecursiveAction：用于没有返回结果的任务。
 *
 * 下面我们以一个例子来说明这个框架如何使用，主要就是创建一个含有10000个资源的List，分别去修改他的内容。
 *
 * </pre>
 */
public class TestRecursiveAction extends RecursiveAction {

    private static final long serialVersionUID = 1L;
    // These attributes will determine the block of products this task has to
    // process.
    private List<Product> products;
    private int first;
    private int last;
    // store the increment of the price of the products
    private double increment;

    public TestRecursiveAction(List<Product> products, int first, int last, double increment) {
        super();
        this.products = products;
        this.first = first;
        this.last = last;
        this.increment = increment;
    }

    /**
     * If the difference between the last and first attributes is greater than or equal to 10,
     * create two new Task objects, one to process the first half of products and the other to
     * process the second half and execute them in ForkJoinPool using the invokeAll() method.
     */
    @Override
    protected void compute() {
        if (last - first < 10) {
            updatePrices();
        } else {
            int middle = (first + last) / 2;
            System.out.printf("Task: Pending tasks:%s\n", getQueuedTaskCount());
            TestRecursiveAction t1 = new TestRecursiveAction(products, first, middle + 1, increment);
            TestRecursiveAction t2 = new TestRecursiveAction(products, middle + 1, last, increment);
            invokeAll(t1, t2);
        }
    }

    private void updatePrices() {
        for (int i = first; i < last; i++) {
            Product product = products.get(i);
            product.setPrice(product.getPrice() * (1 + increment));
        }
    }

    public static void main(String[] args) {

        ProductListGenerator productListGenerator = new ProductListGenerator();
        List<Product> products = productListGenerator.generate(10000);// 生产10000个商品.

        // RecursiveAction task = new TestRecursiveAction(products, 0, products.size(), 0.2);

        ForkJoinTask<?> task = new TestRecursiveAction(products, 0, products.size(), 0.2);

        ForkJoinPool pool = new ForkJoinPool();

        pool.execute(task);

        //所有任务转为ForkJoinTask---> forkOrSubmit--->

        do {
            System.out.printf("Main: Thread Count: %d\n", pool.getActiveThreadCount());
            System.out.printf("Main: Thread Steal: %d\n", pool.getStealCount());
            System.out.printf("Main: Parallelism: %d\n", pool.getParallelism());
            try {
                TimeUnit.MILLISECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (!task.isDone());

        pool.shutdown();

        if (task.isCompletedNormally()) {
            System.out.printf("Main: The process has completed normally.\n");
        }

        for (Product product : products) {
            if (product.getPrice() != 12) {
                System.out.printf("Product %s: %f\n", product.getName(), product.getPrice());
            }
        }

        System.out.println("Main: End of the program.\n");
    }

}

/**
 * generate a list of random products
 */
class ProductListGenerator {

    public List<Product> generate(int size) {
        List<Product> list = new ArrayList<Product>();
        for (int i = 0; i < size; i++) {
            Product product = new Product();
            product.setName("Product" + i);
            product.setPrice(10);
            list.add(product);
        }
        return list;
    }
}

/**
 * store the name and price of a product
 */
class Product {

    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
