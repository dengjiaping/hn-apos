package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.Sorts;
import me.andpay.timobileframework.sqlite.anno.Expression;

public class OrderInfoCond extends Sorts{
	
	/**
	 * 主键编号
	 */
	@Expression
	private Integer idOrder;
	/**
	 * 订单序号
	 */
	@Expression
	private Long orderRecordId;
	/**
	 * 订单编号
	 */
	@Expression
	private String orderId;
	
	/**
	 * 订单金额
	 */
	@Expression
	private Double orderAmt; 
	
	/**
	 * 订单状态
	 */
	@Expression
	private String orderStatus;
	
	@Expression
    private String txnId;

    /**
     * 机构编号
     */
	@Expression
    private String partyId;
    
//    /**
//     * 用户名称
//     */
//	@Expression
//    private String userName;
	
	
    /**
     * 用户名称
     */
	@Expression(paraName = "userName", sqlformat="( ${paraName}=${value} or ${paraName} is NULL)")
    private String userNameFormat;

	/**
	 * 最大id
	 */
	@Expression(paraName = "orderRecordId", operater = "<")
	private Long maxId;
	/**
	 * 最小id
	 */
	@Expression(paraName = "orderRecordId", operater = ">")
	private Long minId;
	
	/**
	 * 开始更新时间
	 */
	@Expression(paraName = "synDate", operater = ">=")
	public String beginSynDate;
	/**
	 * 结束更新时间
	 */
	@Expression(paraName = "synDate", operater = "<")
	public String endSynDate;
	

	/**
	 * 最大交易编号
	 */
	@Expression(paraName = "txnId", operater = "<")
	private String maxTxnId;
	
	/**
	 * 最小交易编号
	 */
	@Expression(paraName = "txnId", operater = ">")
	private String minTxnId;

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}


	public Long getOrderRecordId() {
		return orderRecordId;
	}

	public void setOrderRecordId(Long orderRecordId) {
		this.orderRecordId = orderRecordId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getOrderAmt() {
		return orderAmt;
	}

	public void setOrderAmt(Double orderAmt) {
		this.orderAmt = orderAmt;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
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

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}

	public String getBeginSynDate() {
		return beginSynDate;
	}

	public void setBeginSynDate(String beginSynDate) {
		this.beginSynDate = beginSynDate;
	}

	public String getEndSynDate() {
		return endSynDate;
	}

	public void setEndSynDate(String endSynDate) {
		this.endSynDate = endSynDate;
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

	public String getUserNameFormat() {
		return userNameFormat;
	}

	public void setUserNameFormat(String userNameFormat) {
		this.userNameFormat = userNameFormat;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	
    
}
