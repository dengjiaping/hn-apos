package me.andpay.apos.dao.model;

import java.util.List;

import me.andpay.ac.term.api.txn.order.OrderAttrNameValue;
import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;

/**
 * 
 * @author cpz
 *
 */
@TableName(name = "OrderInfo",version=1)
public class OrderInfo {
	
	/**
	 * 主键编号
	 */
	@ID
	@Column
	private Integer idOrder;
	/**
	 * 订单序号
	 */
	@Column
	private Long orderRecordId;
	/**
	 * 订单编号
	 */
	@Column
	private String orderId;
	
	/**
	 * 订单金额
	 */
	@Column
	private Double orderAmt; 
	
	/**
	 * 订单状态
	 */
    @Column
	private String orderStatus;
    
    /**
     * 同步时间
     */
    @Column
    private String synDate;
    
    /**
     * 创建时间
     */
    @Column
    private String createDate;
    
    /**
     * 过期时间
     */
    @Column
    private String expiredTime;
   
	/**
	 * 订单属性1
	 */
    @Column
	private List<OrderAttrNameValue> orderAttrs1;

	/**
	 * 订单属性2
	 */
    @Column
	private List<OrderAttrNameValue> orderAttrs2;
    
    /**
     * 机构编号
     */
    @Column
    private String partyId;
    
    /**
     * 用户名称
     */
    @Column
    private String userName;
    /**
     * 交易编号
     */
    @Column
    private String txnId;
	

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
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

	public String getSynDate() {
		return synDate;
	}

	public void setSynDate(String synDate) {
		this.synDate = synDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}


	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public List<OrderAttrNameValue> getOrderAttrs1() {
		return orderAttrs1;
	}

	public void setOrderAttrs1(List<OrderAttrNameValue> orderAttrs1) {
		this.orderAttrs1 = orderAttrs1;
	}

	public List<OrderAttrNameValue> getOrderAttrs2() {
		return orderAttrs2;
	}

	public void setOrderAttrs2(List<OrderAttrNameValue> orderAttrs2) {
		this.orderAttrs2 = orderAttrs2;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
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

	public Long getOrderRecordId() {
		return orderRecordId;
	}

	public void setOrderRecordId(Long orderRecordId) {
		this.orderRecordId = orderRecordId;
	}

	
}
