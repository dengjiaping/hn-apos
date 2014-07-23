package me.andpay.apos.cdriver.tlvmodel;

import me.andpay.timobileframework.util.tlv.TlvField;

public class GetOfflineTxnRequest {
	
	@TlvField(value = "FF02", index = 1)
	private byte[] terminalTraceNo;

	public byte[] getTerminalTraceNo() {
		return terminalTraceNo;
	}

	public void setTerminalTraceNo(byte[] terminalTraceNo) {
		this.terminalTraceNo = terminalTraceNo;
	}
	
	
	
}
