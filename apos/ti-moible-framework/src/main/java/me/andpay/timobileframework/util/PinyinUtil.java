package me.andpay.timobileframework.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

	public static String chineseToPinyin(String chineseStr) {

		HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		return chineseToPinyin(chineseStr, hanyuPinyinOutputFormat);

	}

	public static String chineseToPinyin(String chineseStr,
			HanyuPinyinOutputFormat hanyuPinyinOutputFormat) {

		if (chineseStr == null || chineseStr.trim().equals("")) {
			return chineseStr;
		}

		try {
			char[] chars = chineseStr.toCharArray();
			String zhongWenPinYin = "";
			for (int i = 0; i < chars.length; i++) {

				String[] pinYin;

				pinYin = PinyinHelper.toHanyuPinyinStringArray(chars[i],
						hanyuPinyinOutputFormat);

				if (pinYin != null) {
					zhongWenPinYin += pinYin[0];
				} else {
					zhongWenPinYin += chars[i];
				}
			}
			return zhongWenPinYin;
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			return chineseStr;
		}

	}
}
