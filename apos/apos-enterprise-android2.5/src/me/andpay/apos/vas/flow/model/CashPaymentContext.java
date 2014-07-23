package me.andpay.apos.vas.flow.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import me.andpay.apos.vas.spcart.ShoppingCart;

/**
 * 现金支付
 * 
 * @author cpz
 */
public class CashPaymentContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6460587894245958601L;

	/**
	 * 现金支付金额
	 */
	private BigDecimal cashAmt;

	private ShoppingCart shoppingCart;

	/**
	 * 是否可以输入金额
	 */
	private boolean inputAmtFlag;

	private String receiptNo;
	/**
	 * 订单编号
	 */
	private Long orderId;

	private Date orderTime;

	public BigDecimal getCashAmt() {
		return cashAmt;
	}

	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public boolean isInputAmtFlag() {
		return inputAmtFlag;
	}

	public void setInputAmtFlag(boolean inputAmtFlag) {
		this.inputAmtFlag = inputAmtFlag;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

}
