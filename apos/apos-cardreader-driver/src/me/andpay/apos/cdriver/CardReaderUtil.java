package me.andpay.apos.cdriver;

import me.andpay.timobileframework.util.StringConvertor;


public class CardReaderUtil {
	
	public static String panConvert(String pan) {

		if (pan.length() > 12) {
			pan = pan
					.substring(pan.length() - 13, pan.length() - 1);
		}
		long panLong = Long.parseLong(pan);
		pan = StringConvertor.format("0000000000000000",
				panLong % 10000000000000000l);
		return pan;
	}
	
//	public static void main(String[] args) {
//		System.out.print(CardReaderUtil.panConvert("6210300120867285"));
//	}
}
