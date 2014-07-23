package me.andpay.timobileframework.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String BEGINTIMES = "00:00:00";

	public static String ENDTIMES = "23:59:59";

	static final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	public static boolean isBefore(Date date) {

		Calendar calendar = Calendar.getInstance();
		Date curDate = me.andpay.ti.util.DateUtil.roundDate(new Date(), Calendar.DATE);
		calendar.setTime(curDate);
		calendar.add(Calendar.SECOND, -1);
		curDate = calendar.getTime();
		if (date.before(curDate)) {
			return true;
		}

		return false;
	}

	public static Date getCurrentZeroDate() {
		Date current = new Date();
		String zeroPattern = "yyyy-MM-dd";
		return me.andpay.ti.util.DateUtil.parse(zeroPattern,
				me.andpay.ti.util.DateUtil.format(zeroPattern, current));
	}

	public static long dateInterval(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long betweenDays = (time1 - time2) / (1000 * 3600 * 24);
		return betweenDays;
	}

	public static String getCurrentWeekChineseDesc() {
		return getWeekChineseDesc(new Date());
	}

	/**
	 * 获取当前星期描述
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekChineseDesc(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayOfWeek < 0)
			dayOfWeek = 0;
		return dayNames[dayOfWeek];
	}
	
	/**
	 * 
	 * @return long yyMMdd
	 */
	public static long getToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");

		String dateString = formatter.format(currentTime);
		String date10 = dateString.substring(2, 8);// yyMMdd
		int len = date10.length();
		long result = 0;
		for (int i = 0; i < len; i++) {
			result = result * 10 + (date10.charAt(i) - '0');
		}
		return result;
	}

	public static void main(String[] args) {
		Date txnTime = me.andpay.ti.util.DateUtil.parse("yyyyMMddHHmmss",
				"20121224000000");
		System.out.println(DateUtil.isBefore(txnTime));
		System.out.print(DateUtil.dateInterval(new Date(), txnTime));
	}

}
