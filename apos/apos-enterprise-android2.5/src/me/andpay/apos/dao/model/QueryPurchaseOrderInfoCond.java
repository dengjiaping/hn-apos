package me.andpay.apos.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import me.andpay.timobileframework.sqlite.Sorts;
import me.andpay.timobileframework.sqlite.anno.Expression;

public class QueryPurchaseOrderInfoCond extends Sorts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 交易结束时间
	 */
	@Expression(paraName = "compositeQueryField", operater = "like")
	private String compositeQueryField;

	/**
	 * 订单编号
	 */
	@Expression
	private Long orderId;

	@Expression
	private String merchPartyId;

	@Expression
	private String userName;

	@Expression(paraName = "orderId", operater = "<")
	private Long maxOrderId;

	@Expression(paraName = "orderId", operater = ">")
	private Long minOrderId;
	
	@Expression(paraName = "orderTime", operater = "<=")
	private Date maxOrderTime;

	@Expression(paraName = "orderTime", operater = ">=")
	private Date minOrderTime;
	
	@Expression 
	private String paymentMethod;
	
	@Expression(paraName = "updateTime", operater = "<=")
	private Date startUpdateTime;

	@Expression(paraName = "orderTime", operater = "<=")
	private Date endUpdateTime;
	
	/**
	 * 销售金额
	 */
	@Expression
	private BigDecimal salesAmt;
	
	private boolean isHasViewCond;

	public String getCompositeQueryField() {
		return compositeQueryField;
	}

	public void setCompositeQueryField(String compositeQueryField) {
		this.compositeQueryField = compositeQueryField;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getMerchPartyId() {
		return merchPartyId;
	}

	public void setMerchPartyId(String merchPartyId) {
		this.merchPartyId = merchPartyId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getMaxOrderId() {
		return maxOrderId;
	}

	public void setMaxOrderId(Long maxOrderId) {
		this.maxOrderId = maxOrderId;
	}

	public Long getMinOrderId() {
		return minOrderId;
	}

	public void setMinOrderId(Long minOrderId) {
		this.minOrderId = minOrderId;
	}

	public Date getMaxOrderTime() {
		return maxOrderTime;
	}

	public void setMaxOrderTime(Date maxOrderTime) {
		this.maxOrderTime = maxOrderTime;
	}

	public Date getMinOrderTime() {
		return minOrderTime;
	}

	public void setMinOrderTime(Date minOrderTime) {
		this.minOrderTime = minOrderTime;
	}

	public boolean isHasViewCond() {
		return isHasViewCond;
	}

	public void setHasViewCond(boolean isHasViewCond) {
		this.isHasViewCond = isHasViewCond;
	}

	public BigDecimal getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Date getStartUpdateTime() {
		return startUpdateTime;
	}

	public void setStartUpdateTime(Date startUpdateTime) {
		this.startUpdateTime = startUpdateTime;
	}

	public Date getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(Date endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}
	
	
}
