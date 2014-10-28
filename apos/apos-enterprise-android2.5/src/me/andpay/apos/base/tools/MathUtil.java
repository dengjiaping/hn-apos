package me.andpay.apos.base.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;

/**
 * 数学工具
 * 
 * @author Administrator
 *
 */
public class MathUtil {

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int diptoPx(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int pxtoDip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 判断是否为手机号码
	 * 
	 * @param numberStr
	 * @return
	 */
	public static boolean isMobileNumber(String numberStr) {

		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

		Matcher m = p.matcher(numberStr);

		return m.matches();

	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static boolean isIntNumber(String str) {
		try {
			Integer.valueOf(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
