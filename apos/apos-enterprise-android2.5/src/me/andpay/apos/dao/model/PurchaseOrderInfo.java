package me.andpay.apos.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import me.andpay.ac.term.api.shop.PurchaseOrderItem;
import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;
import me.andpay.timobileframework.sqlite.convert.BigDecimalConverter;
import me.andpay.timobileframework.sqlite.convert.DateConverter;
import me.andpay.timobileframework.util.PinyinUtil;

@TableName(name = "PurchaseOrderInfo", version = 1)
public class PurchaseOrderInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8428990454406882360L;

	/**
	 * 消费订单主键
	 */
	@ID
	@Column
	private Integer idOrder;

	/**
	 * 订单编号
	 */
	@Column
	private Long orderId;

	/**
	 * 订单类型
	 */
	@Column
	private String orderType;

	/**
	 * 商户参与者编号
	 */
	@Column
	private String merchPartyId;

	/**
	 * 商户订单编号
	 */
	@Column
	private String merchOrderId;

	/**
	 * 销售币别
	 */
	@Column
	private String salesCur;

	/**
	 * 销售金额
	 */
	@Column(dataConverter = BigDecimalConverter.class)
	private BigDecimal salesAmt;

	/**
	 * 终端编号
	 */
	@Column
	private String terminalId;

	/**
	 * 票据号
	 */
	@Column
	private String receiptNo;

	/**
	 * 用户名
	 */
	@Column
	private String userName;

	/**
	 * 用户姓名
	 */
	@Column
	private String personName;

	/**
	 * 交易地点
	 */
	@Column
	private String location;

	/**
	 * 经度
	 */
	@Column
	private Double latitude;

	/**
	 * 纬度
	 */
	@Column
	private Double longitude;

	/**
	 * 特殊坐标类型
	 */
	@Column
	private String specCoordType;

	/**
	 * 特殊经度
	 */
	@Column
	private Double specLatitude;

	/**
	 * 特殊纬度
	 */
	@Column
	private Double specLongitude;

	/**
	 * 支付方式
	 */
	@Column
	private String paymentMethod;

	/**
	 * 支付交易编号
	 */
	@Column
	private String paymentTxnId;

	/**
	 * 订单时间
	 */
	@Column(dataConverter = DateConverter.class)
	private java.util.Date orderTime;

	/**
	 * 履约时间
	 */
	@Column(dataConverter = DateConverter.class)
	private java.util.Date fulfillTime;

	/**
	 * 退货时间
	 */
	@Column(dataConverter = DateConverter.class)
	private java.util.Date refundTime;

	/**
	 * 属性
	 */
	@Column
	private String attr;

	/**
	 * 状态
	 */
	@Column
	private String status;

	/**
	 * 销售渠道类型
	 */
	@Column
	private String salesChanType;

	/**
	 * 建立时间
	 */
	@Column(dataConverter = DateConverter.class)
	private java.util.Date crtTime;

	/**
	 * 本地更新时间
	 */
	@Column(dataConverter = DateConverter.class)
	private java.util.Date updateTime;

	/**
	 * 项目集
	 */
	@Column
	private List<PurchaseOrderItem> items;

	/**
	 * 组合查询字段
	 */
	@Column
	private String compositeQueryField;

	public String getCompositeQueryField() {
		return compositeQueryField;
	}

	public void setCompositeQueryField(String compositeQueryField) {
		this.compositeQueryField = compositeQueryField;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMerchPartyId() {
		return merchPartyId;
	}

	public void setMerchPartyId(String merchPartyId) {
		this.merchPartyId = merchPartyId;
	}

	public String getMerchOrderId() {
		return merchOrderId;
	}

	public void setMerchOrderId(String merchOrderId) {
		this.merchOrderId = merchOrderId;
	}

	public String getSalesCur() {
		return salesCur;
	}

	public void setSalesCur(String salesCur) {
		this.salesCur = salesCur;
	}

	public BigDecimal getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getSpecCoordType() {
		return specCoordType;
	}

	public void setSpecCoordType(String specCoordType) {
		this.specCoordType = specCoordType;
	}

	public Double getSpecLatitude() {
		return specLatitude;
	}

	public void setSpecLatitude(Double specLatitude) {
		this.specLatitude = specLatitude;
	}

	public Double getSpecLongitude() {
		return specLongitude;
	}

	public void setSpecLongitude(Double specLongitude) {
		this.specLongitude = specLongitude;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentTxnId() {
		return paymentTxnId;
	}

	public void setPaymentTxnId(String paymentTxnId) {
		this.paymentTxnId = paymentTxnId;
	}

	public java.util.Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}

	public java.util.Date getFulfillTime() {
		return fulfillTime;
	}

	public void setFulfillTime(java.util.Date fulfillTime) {
		this.fulfillTime = fulfillTime;
	}

	public java.util.Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(java.util.Date refundTime) {
		this.refundTime = refundTime;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSalesChanType() {
		return salesChanType;
	}

	public void setSalesChanType(String salesChanType) {
		this.salesChanType = salesChanType;
	}

	public java.util.Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(java.util.Date crtTime) {
		this.crtTime = crtTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<PurchaseOrderItem> getItems() {
		return items;
	}

	public void setItems(List<PurchaseOrderItem> items) {

		StringBuffer itemStrBuf = new StringBuffer();

		if (items != null) {
			for (PurchaseOrderItem purchaseOrderItem : items) {
				itemStrBuf
						.append("：")
						.append(purchaseOrderItem.getProductName())
						.append("：")
						.append(PinyinUtil.chineseToPinyin(purchaseOrderItem
								.getProductName())).append("：")
						.append(purchaseOrderItem.getSalesAmt());
			}
		}
		itemStrBuf.append("：");
		itemStrBuf.append(this.getSalesAmt());
		this.compositeQueryField = itemStrBuf.toString();
		this.items = items;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}

}
