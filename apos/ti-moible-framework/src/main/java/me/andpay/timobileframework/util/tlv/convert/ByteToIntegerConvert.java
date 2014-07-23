package me.andpay.timobileframework.util.tlv.convert;

import me.andpay.timobileframework.util.tlv.DataConvertor;

public class ByteToIntegerConvert implements DataConvertor<Integer> {

	public Integer convert(byte[] value) {
		
		return new Integer(value[0]);
	}



	public byte[] convertByte(Object value) {
		return new byte[] { Integer.class.cast(value).byteValue()};
	}

}
