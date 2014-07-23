package me.andpay.apos.vas.flow.model;

import java.io.Serializable;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.vas.spcart.ShoppingCart;

/**
 * 产品销售上下文
 * 
 * @author cpz
 * 
 */
public class ProductSalesContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7030548718160067738L;
	/**
	 * 销售的产品
	 */
	private PurchaseOrder purchaseOrder;

	/**
	 * 支付方式
	 */
	private String paymeneMethed;
	
	
	private ShoppingCart shoppingCart;
	
	
	/**
	 * 销售票据号
	 */
	private String receiptNo;
	
	/**
	 * 支付编号
	 */
	private String paymentTxnId;
	
	

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}


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


}
