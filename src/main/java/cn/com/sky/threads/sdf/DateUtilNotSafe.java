package cn.com.sky.threads.sdf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat类不是线程安全的。 多线程会出现各种不同的情况，比如转化的时间不正确，比如报错，比如线程被挂死等等。
 */
public class DateUtilNotSafe {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String formatDate(Date date) throws ParseException {
		return sdf.format(date);
	}

	public static Date parse(String strDate) throws ParseException {
		return sdf.parse(strDate);
	}
}