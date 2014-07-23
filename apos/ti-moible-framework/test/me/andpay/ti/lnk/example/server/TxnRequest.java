package me.andpay.ti.lnk.example.server;

import java.math.BigDecimal;

/**
 * 交易请求类。
 * 
 * @author sea.bao
 */
public class TxnRequest {
	private String cardNo;
	
	private BigDecimal amt;
	
	private java.util.Date txnTime;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public java.util.Date getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(java.util.Date txnTime) {
		this.txnTime = txnTime;
	}
	
}
