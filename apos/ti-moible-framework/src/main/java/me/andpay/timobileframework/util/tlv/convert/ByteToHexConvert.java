package me.andpay.timobileframework.util.tlv.convert;

import me.andpay.timobileframework.util.HexUtils;
import me.andpay.timobileframework.util.tlv.DataConvertor;

public class ByteToHexConvert implements DataConvertor<String> {

	public String convert(byte[] value) {
		if(value == null) {
			return null;
		}
		return HexUtils.bytesToHexString(value);
	}

	public byte[] convertByte(Object value) {
		if(value == null) {
			return null;
		}
		
		return HexUtils.hexString2Bytes(value.toString());
	}

	


}
