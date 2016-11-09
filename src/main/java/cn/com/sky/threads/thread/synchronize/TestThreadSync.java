package cn.com.sky.threads.thread.synchronize;

/**
 * <pre>
 * 1.对象锁：
 * 假设有一个类ClassA，其中有一个方法synchronized methodA()，
 * 那么当这个方法被调用的时候获得就是对象锁，
 * 但是要注意，如果这个类有两个实例:
 * 比如：
 * ClassA a = new ClassA(); 
 * ClassA b = new ClassA();
 * 那么如果你在a这对象上调用了methodA，不会影响b这个对象，也就是说对于b这个对象，他也可以调用methodA，
 * 因为这是两对象， 所以说对象锁是针对对象的。
 * 
 * 2.类锁
 * 类锁，其实没有所谓的类锁，因为类锁实际上就是这个类的对象的对象锁，
 * 例如有一个类ClassA，其中有一个方法synchronized static methodA()，
 * 注意这个方法是静态的，那就是说这个类的所有的对象都公用一个这个方法了，那如果你在这个类的某个对象上调用了这个方法，
 * 那么其他的对象如果想要用这个方法就得等着锁被释放，所以感觉就好像这个类被锁住了一样。
 * </pre>
 */
