package me.andpay.util;

import me.andpay.timobileframework.util.tlv.ConvertData;
import me.andpay.timobileframework.util.tlv.TlvField;
import me.andpay.timobileframework.util.tlv.convert.ByteToHexConvert;

public class TlvObject {
	
	
	@TlvField(value="9F06",index=1)
	@ConvertData(value=ByteToHexConvert.class)
	private String terminalTraceNo = "A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010A0000000031010";
	
	@TlvField(value="09",index=2)
	@ConvertData(value=ByteToHexConvert.class)
	private  String timeOut = "0101";
	

	
	public String getTerminalTraceNo() {
		return terminalTraceNo;
	}

	public void setTerminalTraceNo(String terminalTraceNo) {
		this.terminalTraceNo = terminalTraceNo;
	}
	
	public String getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}


	
	
}
