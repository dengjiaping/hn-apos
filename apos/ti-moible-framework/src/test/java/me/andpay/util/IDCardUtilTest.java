package me.andpay.util;

import me.andpay.timobileframework.util.StringConvertor;

import org.junit.Test;



public class IDCardUtilTest {
	@Test
	public void testID() {
//		Assert.assertEquals(true, IDCardUtil.validate("330282198410204996")); 
//		Assert.assertEquals(false, IDCardUtil.validate("330282198410204946")); 
//		Assert.assertEquals(false, IDCardUtil.validate("330282238410204946")); 
//		Assert.assertEquals(true, IDCardUtil.validate("34052419800101001X")); 
		System.out.print(IDCardUtilTest.panConvert("6210300120867285"));

		
		
	}
	
	
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
}
