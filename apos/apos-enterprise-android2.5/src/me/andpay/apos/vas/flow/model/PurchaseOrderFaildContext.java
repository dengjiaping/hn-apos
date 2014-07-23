package me.andpay.apos.vas.flow.model;

import java.io.Serializable;
import java.util.Date;

import me.andpay.apos.vas.spcart.ShoppingCart;

public class PurchaseOrderFaildContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ShoppingCart shoppingCart;

	private String receiptNo;

	private String txnId;

	private String paymentMethod;
	
	private String msgContent;
	
	/**
	 * 是否显示退出按钮
	 */
	private boolean showOutButton;
	
	private Long orderId;
	
	private Date orderTime;
	
	


	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public boolean isShowOutButton() {
		return showOutButton;
	}

	public void setShowOutButton(boolean showOutButton) {
		this.showOutButton = showOutButton;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	
	

}
