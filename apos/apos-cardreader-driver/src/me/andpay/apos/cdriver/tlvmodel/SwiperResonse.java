package me.andpay.apos.cdriver.tlvmodel;

import me.andpay.timobileframework.util.tlv.ConvertData;
import me.andpay.timobileframework.util.tlv.TlvField;
import me.andpay.timobileframework.util.tlv.convert.ByteToHexConvert;

public class SwiperResonse {
	
	
	@TlvField("FF07")
	private byte[] encPin;
	
	@TlvField("FF08")
	@ConvertData(ByteToHexConvert.class)
	private String encTracks;
	
	@TlvField("FF01")
	@ConvertData(ByteToHexConvert.class)
	private String ksn;

	

	public byte[] getEncPin() {
		return encPin;
	}

	public void setEncPin(byte[] encPin) {
		this.encPin = encPin;
	}

	public String getEncTracks() {
		return encTracks;
	}

	public void setEncTracks(String encTracks) {
		this.encTracks = encTracks;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}
	
	
	
}
