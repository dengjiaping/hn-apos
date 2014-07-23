package me.andpay.apos.cdriver.tlvmodel;

import java.math.BigDecimal;

import me.andpay.timobileframework.util.tlv.ConvertData;
import me.andpay.timobileframework.util.tlv.TlvField;
import me.andpay.timobileframework.util.tlv.convert.ByteToAmtConvert;
import me.andpay.timobileframework.util.tlv.convert.ByteToHexConvert;

public class GetOffileTxnResponse {
	
	@TlvField(value="FF0A",index=1)
	@ConvertData(ByteToHexConvert.class)
	private String respnseCode;
	
	@TlvField(value="FF04",index=2)
	@ConvertData(ByteToAmtConvert.class)
	private BigDecimal amt;
	
	
	@TlvField(value="FF07",index=3)
	private byte[] encPin;
	
	@TlvField(value="FF08",index=4)
	@ConvertData(ByteToHexConvert.class)
	private String encTracks;
	
	@TlvField(value="FF01",index=5)
	@ConvertData(ByteToHexConvert.class)
	private String ksn;

	public String getRespnseCode() {
		return respnseCode;
	}

	public void setRespnseCode(String respnseCode) {
		this.respnseCode = respnseCode;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

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
