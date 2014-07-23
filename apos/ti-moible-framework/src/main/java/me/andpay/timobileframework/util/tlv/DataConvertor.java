package me.andpay.timobileframework.util.tlv;


public interface DataConvertor<T> {

	T convert(byte[] value) ;
	
	
	byte[] convertByte(Object value) ;

}