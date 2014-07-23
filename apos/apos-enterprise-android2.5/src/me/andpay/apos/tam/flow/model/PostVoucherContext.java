package me.andpay.apos.tam.flow.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import me.andpay.apos.vas.spcart.ShoppingCart;

/**
 * 凭证发送上下文
 * 
 * @author cpz
 * 
 */
public class PostVoucherContext implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6829107173230903293L;
	/**
	 * 联系方式集合
	 */
	private Map<String, String> contactInfos;

	/**
	 * 重发凭证标志
	 */
	private boolean rePostFlag;
	
	/**
	 * 是否有新交易按钮
	 */
	private boolean hasNewTxnButton;
	
	
	/**
	 * 销售票据号
	 */
	private String receiptNo;
	/*
	 * 购物车
	 */
	private ShoppingCart shoppingCart;
	
	private String txnId;
	
	private String formatedAmt;

	private String message;
	
	private Date orderTime;
	
	private Date settlementTime;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Map<String, String> getContactInfos() {
		return contactInfos;
	}

	public void setContactInfos(Map<String, String> contactInfos) {
		this.contactInfos = contactInfos;
	}

	public boolean isRePostFlag() {
		return rePostFlag;
	}

	public void setRePostFlag(boolean rePostFlag) {
		this.rePostFlag = rePostFlag;
	}

	public boolean isHasNewTxnButton() {
		return hasNewTxnButton;
	}

	public void setHasNewTxnButton(boolean hasNewTxnButton) {
		this.hasNewTxnButton = hasNewTxnButton;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	
	public String getFormatedAmt() {
		return formatedAmt;
	}

	public void setFormatedAmt(String formatedAmt) {
		this.formatedAmt = formatedAmt;
	}
	
	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

}
