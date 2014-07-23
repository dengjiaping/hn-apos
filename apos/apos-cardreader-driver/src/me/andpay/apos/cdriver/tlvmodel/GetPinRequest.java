package me.andpay.apos.cdriver.tlvmodel;

import java.math.BigDecimal;

import me.andpay.timobileframework.util.tlv.ConvertData;
import me.andpay.timobileframework.util.tlv.TlvField;
import me.andpay.timobileframework.util.tlv.convert.ByteToAmtConvert;
import me.andpay.timobileframework.util.tlv.convert.ByteToHexConvert;
import me.andpay.timobileframework.util.tlv.convert.ByteToIntegerConvert;

public class GetPinRequest {

	@TlvField(value = "FF02", index = 1)
	private byte[] terminalTraceNo;

	@TlvField(value = "FF03", index = 2)
	@ConvertData(ByteToHexConvert.class)
	private String pan;

	@TlvField(value = "FF04", index = 3)
	@ConvertData(ByteToAmtConvert.class)
	private BigDecimal amt;

	@TlvField(value = "FF05", index = 4)
	@ConvertData(ByteToIntegerConvert.class)
	private Integer pinTimeout;

	public byte[] getTerminalTraceNo() {
		return terminalTraceNo;
	}

	public void setTerminalTraceNo(byte[] terminalTraceNo) {
		this.terminalTraceNo = terminalTraceNo;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Integer getPinTimeout() {
		return pinTimeout;
	}

	public void setPinTimeout(Integer pinTimeout) {
		this.pinTimeout = pinTimeout;
	}

}
