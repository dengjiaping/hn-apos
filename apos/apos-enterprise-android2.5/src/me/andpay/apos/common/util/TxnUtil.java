package me.andpay.apos.common.util;

import java.util.Date;

import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.util.StringConvertor;

public class TxnUtil {
	/**
	 * 起始终端流水号
	 */
	private static String FIRST_TRACE_NO = "1";

	public static String getReceipNo(TiContext tiConfig) {

		String dateStr = StringUtil.format("yyyyMMdd", new Date());
		String receipNo = (String) tiConfig
				.getAttribute(ConfigAttrNames.RECEIP_NO);
		if (StringUtil.isBlank(receipNo)) {
			receipNo = dateStr + "0001";
		} else {
			String subDateStr = receipNo.substring(0, 8);
			String subNoStr = receipNo.substring(8, 12);

			if (dateStr.equals(subDateStr)) {
				Long strLong = Long.valueOf(subNoStr);
				strLong = strLong + 1;
				String str = StringConvertor.format("0000", strLong % 10000);
				receipNo = dateStr + str;
			} else {
				receipNo = dateStr + "0001";
			}
		}
		tiConfig.setAttribute(ConfigAttrNames.RECEIP_NO, receipNo);
		return receipNo;
	}

	/**
	 * 获取终端流水号
	 * 
	 * @param tiContext
	 */
	public static String getTermTraceNo(TiContext tiConfig) {
		String termTraceNo = (String) tiConfig
				.getAttribute(ConfigAttrNames.TERM_TRACENO);
		if (StringUtil.isBlank(termTraceNo)) {
			termTraceNo = FIRST_TRACE_NO;
			tiConfig.setAttribute(ConfigAttrNames.TERM_TRACENO, termTraceNo);
		}
		Long strLong = Long.valueOf(termTraceNo);
		String str = StringConvertor.format("000000", strLong % 1000000);
		return str;
	}

	public static void updateTermTraceNo(TiContext tiConfig) {
		String termTraceNo = (String) tiConfig
				.getAttribute(ConfigAttrNames.TERM_TRACENO);
		Long strLong = Long.valueOf(termTraceNo);
		tiConfig.setAttribute(ConfigAttrNames.TERM_TRACENO, strLong + 1l);
	}

	public static String hidePan(String pan) {

		if (StringUtil.isBlank(pan)) {
			return null;
		}

		if (pan.length() < 10) {
			return pan;
		}
		return pan.substring(0, 6) + "******"
				+ pan.substring(pan.length() - 4, pan.length());

	}

	public static String formatCardNo(String cardNo) {
		int length = cardNo.length();
		StringBuffer formatCardNo = new StringBuffer();
		for (int i = 0; i < length; i++) {
			if (i != 0 && i % 4 == 0) {
				formatCardNo.append(" ");
			}
			formatCardNo.append(cardNo.charAt(i));
		}
		return formatCardNo.toString();
	}

}
