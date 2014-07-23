package me.andpay.timobileframework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

import me.andpay.ti.util.StringUtil;

public class StringConvertor {

	public static String convert2Currency(double currency) {
		return String.format("￥%1$,.2f", currency);
	}

	public static String convert2Currency(BigDecimal currency) {
		return String.format("￥%1$,.2f", currency);
	}

	public static String convert2Currency(String currency) {
		return String.format("￥%1$,.2f", Double.valueOf(currency));
	}

	/**
	 * 
	 * @param fomatAmt
	 *            带获取符号字符串
	 * @return
	 */
	public static boolean isAmtBank(String fomatAmt) {
		if (StringUtil.isBlank(fomatAmt)) {
			return true;
		}
		Number amt  = StringConvertor.parse(fomatAmt);
		if(amt.toString().equals("0")) {
			return true;
		}
		return false;
	}

	public static Number parse(String amtStr) {

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
		try {
			return format.parse(amtStr);
		} catch (ParseException e) {
			return null;
		}
	}
	

	public static BigDecimal parseToBigDecimal(String amtStr) {

		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
		try {
			return new BigDecimal(format.parse(amtStr).toString());
		} catch (ParseException e) {
			return null;
		}
	}

	public static String format(BigDecimal amt) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
		return format.format(amt);

	}

	public static String convertCurrency2Str(String amtFomat) {
		boolean isNegative = false;
		if (amtFomat.indexOf("-") > 0) {
			amtFomat = amtFomat.replace("-", "");
			isNegative = true;
		}
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
		
		Number amt = null;
		try {
			amt = format.parse(amtFomat);
			String amtStr = amt.toString();
			if (isNegative) {
				amtStr = "-" + amtStr;
			}

			return amtStr;
		} catch (ParseException e) {

		}
		return null;
	}

	public static String format(String pattern, Long n) {
		if (n == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(n);
	}

	public static String filterEmptyString(String str) {
		if (str == null || "".equals(str.trim())) {
			return null;
		}
		return str;
	}
	
	
	public static boolean isNumber(String str) {
		
		Pattern p = Pattern.compile("\\d+");
		java.util.regex.Matcher m = p.matcher(str.toString());
		
		return m.matches(); 
	}
	
	/**
	 * 
	 * @param strInt
	 * @return null or empty return 0
	 */
	public static int parseInt(String strInt) {
		return parseInt(strInt, 0);
	}
	
	/**
	 * 
	 * @param strInt
	 * @return null or empty return defaultValue
	 */
	public static int parseInt(String strInt, int defaultValue) {
		if (strInt == null || "".equals(strInt))
			return defaultValue;
		try {
			return Integer.parseInt(strInt);
		} catch (Exception e) {
			return defaultValue;
		}
	}

}
