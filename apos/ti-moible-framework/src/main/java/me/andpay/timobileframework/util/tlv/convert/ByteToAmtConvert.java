package me.andpay.timobileframework.util.tlv.convert;

import java.math.BigDecimal;

import me.andpay.timobileframework.util.tlv.DataConvertor;

public class ByteToAmtConvert implements DataConvertor<BigDecimal> {

	public BigDecimal convert(byte[] value) {
		
		return new BigDecimal(new String(value)).divide(new BigDecimal("100"));
	}

	public byte[] convertByte(Object value) {
		if(value == null) {
			return null;
		}
		
		BigDecimal amt = new BigDecimal(value.toString());
		amt = amt.multiply(new BigDecimal(100));
		return String.valueOf(amt.intValue()).getBytes();
	}


}
