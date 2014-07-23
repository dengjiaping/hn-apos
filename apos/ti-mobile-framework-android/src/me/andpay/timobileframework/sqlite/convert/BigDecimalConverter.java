package me.andpay.timobileframework.sqlite.convert;

import java.math.BigDecimal;

public class BigDecimalConverter implements DataConverter<String, BigDecimal>{

	public String convertToString(BigDecimal f) {
		if(f==null){
			return null;
		}
		f = f.multiply(new BigDecimal("1.00"));
		return f.toString();
	}
	public BigDecimal convertToObj(String t) {
		if(t==null) {
			return BigDecimal.ZERO;
		}
		return new BigDecimal(t);
	}

}
