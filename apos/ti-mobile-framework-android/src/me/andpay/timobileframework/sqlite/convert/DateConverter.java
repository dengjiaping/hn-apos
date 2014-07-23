package me.andpay.timobileframework.sqlite.convert;

import java.util.Date;

import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;

public class DateConverter implements DataConverter<String, Date> {

	private static final String pattern = "yyyyMMddHHmmss";

	public String convertToString(Date date) {

		if (date == null) {
			return null;
		}

		return DateUtil.format(pattern, date);
	}


	public Date convertToObj(String str) {
		if (StringUtil.isBlank(str)) {
			return null;
		}

		return DateUtil.parse(pattern, str);
	}

}
