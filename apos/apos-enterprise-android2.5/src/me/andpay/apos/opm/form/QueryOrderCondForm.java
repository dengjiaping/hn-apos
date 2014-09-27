package me.andpay.apos.opm.form;

import java.math.BigDecimal;

public class QueryOrderCondForm {

	/**
	 * 编号
	 */
	private Long orderRecordId;

	/**
	 * 最大编号
	 */
	private Long maxId;

	/**
	 * 最小编号
	 */
	private Long minId;

	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 金额
	 */
	private BigDecimal amt;

	/**
	 * 交易参与者编号
	 */
	private String txnPartyId;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 交易编号
	 */
	private String txnId;

	/**
	 * 状态
	 */
	private String status;
	/**
	 * 排序字段
	 */
	private String orders;

	/**
	 * 最大交易编号
	 */
	private String maxTxnId;

	/**
	 * 最小交易编号
	 */
	private String minTxnId;

	public Long getOrderRecordId() {
		return orderRecordId;
	}

	public void setOrderRecordId(Long orderRecordId) {
		this.orderRecordId = orderRecordId;
	}

	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public Long getMinId() {
		return minId;
	}

	public void setMinId(Long minId) {
		this.minId = minId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public String getTxnPartyId() {
		return txnPartyId;
	}

	public void setTxnPartyId(String txnPartyId) {
		this.txnPartyId = txnPartyId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

	public String getMaxTxnId() {
		return maxTxnId;
	}

	public void setMaxTxnId(String maxTxnId) {
		this.maxTxnId = maxTxnId;
	}

	public String getMinTxnId() {
		return minTxnId;
	}

	public void setMinTxnId(String minTxnId) {
		this.minTxnId = minTxnId;
	}

}
