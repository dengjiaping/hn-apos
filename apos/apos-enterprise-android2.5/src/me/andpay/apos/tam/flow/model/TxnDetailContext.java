package me.andpay.apos.tam.flow.model;

import java.io.Serializable;
import java.util.Date;

public class TxnDetailContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2877569706193701981L;

	private String salesAmtFomat;

	private String extTraceNo;

	private String memo;

	private String cardNo;

	private String txnType;

	private String txnTypeName;

	private String txnAddress;

	private String cardIssuerName;

	private Date txnTime;

	private String cardAssoc;

	private String goodsFileURL;

	private String signFileURL;

	public String getCardIssuerName() {
		return cardIssuerName;
	}

	public void setCardIssuerName(String cardIssuerName) {
		this.cardIssuerName = cardIssuerName;
	}

	public String getSalesAmtFomat() {
		return salesAmtFomat;
	}

	public void setSalesAmtFomat(String salesAmtFomat) {
		this.salesAmtFomat = salesAmtFomat;
	}

	public String getExtTraceNo() {
		return extTraceNo;
	}

	public void setExtTraceNo(String extTraceNo) {
		this.extTraceNo = extTraceNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	//
	// public boolean isTxnfinish() {
	// return txnfinish;
	// }
	//
	// public void setTxnfinish(boolean txnfinish) {
	// this.txnfinish = txnfinish;
	// }

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnTypeName() {
		return txnTypeName;
	}

	public void setTxnTypeName(String txnTypeName) {
		this.txnTypeName = txnTypeName;
	}

	public String getTxnAddress() {
		return txnAddress;
	}

	public void setTxnAddress(String txnAddress) {
		this.txnAddress = txnAddress;
	}

	public Date getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}

	public String getCardAssoc() {
		return cardAssoc;
	}

	public void setCardAssoc(String cardAssoc) {
		this.cardAssoc = cardAssoc;
	}

	public String getGoodsFileURL() {
		return goodsFileURL;
	}

	public void setGoodsFileURL(String goodsFileURL) {
		this.goodsFileURL = goodsFileURL;
	}

	public String getSignFileURL() {
		return signFileURL;
	}

	public void setSignFileURL(String signFileURL) {
		this.signFileURL = signFileURL;
	}

}
