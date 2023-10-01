package cn.com.sky.threads.thread.thread_local;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 由于创建一个 SimpleDateFormat 实例的开销比较昂贵，解析字符串时间时频繁创建生命周期短暂的实例导致性能低下。
 * <p>
 * 所以将 SimpleDateFormat 定义为静态类变量。但是 SimpleDateFormat 是非线程安全的。
 * <p>
 * 如果用 synchronized 线程同步同样面临问题，同步导致性能下降。
 * <p>
 * 使用 Threadlocal 解决了此问题，为每个线程创建一个 SimpleDateFormat 变量的拷贝。
 * <p>
 * <p>
 * <p>
 * 使用ThreadLocal以空间换时间解决SimpleDateFormat线程安全问题。
 */
public class DateFormatUtils {

    // 创建一个ThreadLocal类变量，这里创建时用了一个匿名类，覆盖了initialValue方法，主要作用是创建时初始化实例。
    private static final ThreadLocal threadLocal = new ThreadLocal();
//    {
////        protected synchronized Object initialValue() {
////            return new SimpleDateFormat();
////        }
//    };

    // 获取线程的变量副本，如果不覆盖initialValue，第一次get返回null，故需要初始化一个SimpleDateFormat，并set到threadLocal中
    public static DateFormat getDateFormat(String pattern) {
        DateFormat dateFormat = (DateFormat) threadLocal.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(pattern);
            threadLocal.set(dateFormat);
        }
        return dateFormat;
    }

    public static Date parse(String pattern, String textDate) throws ParseException {
        return getDateFormat(pattern).parse(textDate);
    }

    public static String format(String pattern, Date date) {
        return getDateFormat(pattern).format(date);
    }
}