package me.andpay.util;

import me.andpay.timobileframework.util.PinyinUtil;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

import org.junit.Assert;
import org.junit.Test;

public class PinyinUtilTest {
	
	@Test
	public void testConvertPinyin() {
		
		String pinyin = PinyinUtil.chineseToPinyin("大家好1234");
		Assert.assertTrue(pinyin.equals("DAJIAHAO1234"));
	}
	
	
	@Test
	public void testConvertPinyinError() {
		
		String pinyin = PinyinUtil.chineseToPinyin("");
		Assert.assertTrue(pinyin.equals(""));
		
		
		HanyuPinyinOutputFormat hanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
		hanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.UPPERCASE);
		hanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITH_TONE_MARK);
		hanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		String pinyin1 = PinyinUtil.chineseToPinyin("大家好",hanyuPinyinOutputFormat);
		Assert.assertTrue(pinyin1.equals("大家好"));


	}
}
