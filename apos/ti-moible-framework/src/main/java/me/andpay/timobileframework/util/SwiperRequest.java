package me.andpay.timobileframework.util;

import java.math.BigDecimal;

import me.andpay.timobileframework.util.tlv.ConvertData;
import me.andpay.timobileframework.util.tlv.TlvField;
import me.andpay.timobileframework.util.tlv.convert.ByteToAmtConvert;
import me.andpay.timobileframework.util.tlv.convert.ByteToHexConvert;
import me.andpay.timobileframework.util.tlv.convert.ByteToIntegerConvert;

public class SwiperRequest {
	
	@TlvField(value="FF09",index=1)
	@ConvertData(ByteToIntegerConvert.class)
	private Integer opModel;
	
	@TlvField(value="FF02",index=2)
	@ConvertData(ByteToHexConvert.class)
	private String terminalTraceNo;
	
	@TlvField(value="FF04",index=3)
	@ConvertData(ByteToAmtConvert.class)
	private BigDecimal amt;
	
	@TlvField(value="FF05",index=4)
	@ConvertData(ByteToIntegerConvert.class)
	private Integer pinTimeout;
	
	@TlvField(value="FF06",index=5)
	@ConvertData(ByteToIntegerConvert.class)
	private Integer swiperTimeout;

	public Integer getOpModel() {
		return opModel;
	}

	public void setOpModel(Integer opModel) {
		this.opModel = opModel;
	}

	public String getTerminalTraceNo() {
		return terminalTraceNo;
	}

	public void setTerminalTraceNo(String terminalTraceNo) {
		this.terminalTraceNo = terminalTraceNo;
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

	public Integer getSwiperTimeout() {
		return swiperTimeout;
	}

	public void setSwiperTimeout(Integer swiperTimeout) {
		this.swiperTimeout = swiperTimeout;
	}
	
	
	
}
