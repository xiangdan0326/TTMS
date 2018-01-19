package cn.tedu.ttms.common.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * ThreadLocal是Java中的一个API,此对象提供了
 * 这样的一种机制,能够将某个对象绑定到当前线程
 * 也可以从当前线程获取某个对象,目的是保证线程
 * 内部单例(某个类的实例在当前线程中只有一份)
 * 
 * @author adminitartor
 *
 */
public class DateFormatUtil2 {//ThreadLocal
	private static volatile ThreadLocal<SimpleDateFormat> td=
			new ThreadLocal<SimpleDateFormat>(){
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy/MM/dd");
		};
	};

	//静态同步方法默认使用的对象锁为(类名.class)
	public static  String format(Date date){
		SimpleDateFormat sdf=td.get();
		System.out.println(sdf);
		return sdf.format(date);
	}
	public static   Date parse(String str)
			throws ParseException{
		return td.get().parse(str);
	}
	public static void main(String[] args) {
		format(new Date());//toString()
		format(new Date());
	}
}










