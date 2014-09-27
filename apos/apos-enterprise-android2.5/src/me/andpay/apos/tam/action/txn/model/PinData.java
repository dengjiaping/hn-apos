package me.andpay.apos.tam.action.txn.model;

/**
 * 密码数据
 * 
 * @author cpz
 *
 */
public class PinData {

	private byte[] pinblock;

	private byte[] pinEncryptAdditionData;

	private String pinEncryptMethods;

	public byte[] getPinblock() {
		return pinblock;
	}

	public void setPinblock(byte[] pinblock) {
		this.pinblock = pinblock;
	}

	public byte[] getPinEncryptAdditionData() {
		return pinEncryptAdditionData;
	}

	public void setPinEncryptAdditionData(byte[] pinEncryptAdditionData) {
		this.pinEncryptAdditionData = pinEncryptAdditionData;
	}

	public String getPinEncryptMethods() {
		return pinEncryptMethods;
	}

	public void setPinEncryptMethods(String pinEncryptMethods) {
		this.pinEncryptMethods = pinEncryptMethods;
	}
}
