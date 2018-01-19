package cn.tedu.ttms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class DateUtil {
	private static SimpleDateFormat sdf=
			new SimpleDateFormat();
	//静态同步方法默认使用的对象锁为(类名.class)
	public static synchronized String format(Date date){
		return sdf.format(date);
	}
	public static synchronized Date parse(String str)
			throws ParseException{
		return sdf.parse(str);
	}
}
