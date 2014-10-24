package me.andpay.apos.base.tools;

/**
 * 字符串工具类
 * 
 * @author Administrator
 *
 */
public class StringUtil {
	/**
	 * 判断一个字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str==null||"".equals(str) || " ".equals(str)||"[]".equals(str)) {
			return true;
		}
		return false;

	}
}
