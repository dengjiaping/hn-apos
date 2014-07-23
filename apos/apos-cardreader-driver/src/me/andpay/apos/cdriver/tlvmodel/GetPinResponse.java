package me.andpay.apos.cdriver.tlvmodel;

import me.andpay.timobileframework.util.tlv.TlvField;


/**
 * 获取pin响应
 * @author cpz
 *
 */
public class GetPinResponse {

	@TlvField(value="FF07",index=1)
	private byte[] encPin;

	public byte[] getEncPin() {
		return encPin;
	}

	public void setEncPin(byte[] encPin) {
		this.encPin = encPin;
	}
	
	
	

}
