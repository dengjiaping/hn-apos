package me.andpay.apos.trm.flow.model;

import java.io.Serializable;
import java.math.BigDecimal;

import me.andpay.apos.dao.model.PayTxnInfo;

public class RefundInputContext implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 已退货货金额
	 */
	private BigDecimal hasRefundAmt;
	/**
	 * 可退货金额
	 */
	private BigDecimal refundAmt;
	
	private String exTraceNO;
	
	
	private String memo;
	
	
	private PayTxnInfo payTxnInfo;

	
	public BigDecimal getHasRefundAmt() {
		return hasRefundAmt;
	}
	public void setHasRefundAmt(BigDecimal hasRefundAmt) {
		this.hasRefundAmt = hasRefundAmt;
	}
	public BigDecimal getRefundAmt() {
		return refundAmt;
	}
	public void setRefundAmt(BigDecimal refundAmt) {
		this.refundAmt = refundAmt;
	}
	public String getExTraceNO() {
		return exTraceNO;
	}
	public void setExTraceNO(String exTraceNO) {
		this.exTraceNO = exTraceNO;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public PayTxnInfo getPayTxnInfo() {
		return payTxnInfo;
	}
	public void setPayTxnInfo(PayTxnInfo payTxnInfo) {
		this.payTxnInfo = payTxnInfo;
	}

	
	
	
	
}
