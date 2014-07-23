package me.andpay.apos.vas.form;

import java.util.Date;

import me.andpay.apos.vas.spcart.ShoppingCart;

public class PurchaseOrderForm {
	
	/**
	 * 支付方式
	 */
	private String paymeneMethed;
	
	private ShoppingCart shoppingCart;
	/**
	 * 订单状态
	 */
	private String purchaseStatus;
	/**
	 * 销售票据号
	 */
	private String receiptNo;
	
	/**
	 * 支付编号
	 */
	private String paymentTxnId;
	
	private Date orderTime;
	
	public String getPaymeneMethed() {
		return paymeneMethed;
	}

	public void setPaymeneMethed(String paymeneMethed) {
		this.paymeneMethed = paymeneMethed;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public String getPurchaseStatus() {
		return purchaseStatus;
	}

	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public String getPaymentTxnId() {
		return paymentTxnId;
	}

	public void setPaymentTxnId(String paymentTxnId) {
		this.paymentTxnId = paymentTxnId;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	

}
