package me.andpay.apos.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;

import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;
import me.andpay.timobileframework.sqlite.convert.BigDecimalConverter;

/**
 * 交易表
 * 
 * @author cpz
 */
@TableName(name = "PayTxnInfo",version=4)
public class PayTxnInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键编号
	 */
	@ID
	@Column
	private Integer idTxn;

	/**
	 * 交易关联编号
	 */
	@Column
	private String refNo;

	/**
	 * 原交易关联编号
	 */
	@Column
	private String origRefNo;

	/**
	 * 外部跟踪编号
	 */
	@Column
	private String exTraceNO;
	/**
	 * 交易类型
	 */
	@Column
	private String txnType;
	/**
	 * 交易状态
	 */
	@Column
	private String txnStatus;
	/**
	 * 交易状态标志
	 */
	@Column
	private String txnFlag;
	/**
	 * 交易状态标志描述
	 */
	@Column
	private String txnFlagMessage;

	/**
	 * 金额
	 */
	@Column(dataConverter=BigDecimalConverter.class)
	private BigDecimal salesAmt;
	/**
	 * 终端批次号
	 */
	@Column
	private String termBatchNo;
	/**
	 * 终端流水号
	 */
	@Column
	private String termTraceNo;
	/**
	 * 交易时间
	 */
	@Column
	private String txnTime;
	/**
	 * 终端交易时间
	 */
	@Column
	private String termTxnTime;
	/**
	 * 商户编号
	 */
	@Column
	private String txnPartyId;
	/**
	 * 商户名称
	 */
	@Column
	private String txnPartyName;
	/**
	 * 商户编号
	 */
	@Column
	private String merchantId;
	/**
	 * 商户名称
	 */
	@Column
	private String merchantName;
	/**
	 * 分支机构
	 */
	@Column
	private String subPartyNo;
	/**
	 * 分支机构名称
	 */
	@Column
	private String subPartyName;
	/**
	 * 缩略卡号
	 */
	@Column
	private String shortPan;

	/**
	 * 操作员编号
	 */
	@Column
	private String operNo;
	/**
	 * 操作员名称
	 */
	@Column
	private String operName;

	/**
	 * 退货审核人编号
	 */
	@Column
	private String refundAuditOper;
	/**
	 * 经度
	 */
	@Column
	private Double longitude;
	/**
	 * 纬度
	 */
	@Column
	private Double latitude;
	/**
	 * 银行授权编号
	 */
	@Column
	private String bankAuthNo;
	/**
	 * 备注
	 */
	@Column
	private String memo;
	/**
	 * 返回码
	 */
	@Column
	private String respCode;
	/**
	 * 返回信息
	 */
	@Column
	private String respMsg;
	/**
	 * KSN
	 */
	@Column
	private String ksn;
	/**
	 * IMEI
	 */
	@Column
	private String imei;
	/**
	 * 手机型号
	 */
	@Column
	private String phone;
	/**
	 * 软件版本
	 */
	@Column
	private String appVersion;
	/**
	 * 持卡人手机
	 */
	@Column
	private String holPhoneNO;
	/**
	 * 持卡人email
	 */
	@Column
	private String holEmail;
	/**
	 * 持卡人微博
	 */
	@Column
	private String holMicroblog;

	/**
	 * 卡别
	 */
	@Column
	private String cardType;
	/**
	 * 卡组织
	 */
	@Column
	private String cardOrg;
	/**
	 * 交易状态描述
	 */
	@Column
	private String txnStatusDesc;
	/**
	 * 交易图片ID
	 */
	@Column
	private String tranPic;
	/**
	 * 电子签名图片ID
	 */
	@Column
	private String signPic;
	/**
	 * 位置信息描述
	 */
	@Column
	private String positionDesc;
	/**
	 * 交易类型描述
	 */
	@Column
	private String txnTypeDesc;

	/**
	 * 退货金额
	 */
	@Column(dataConverter=BigDecimalConverter.class)
	private BigDecimal refundAmt;
	/**
	 * 机构编号
	 */
	@Column
	private String issuerId;
	/**
	 * 机构名称
	 */
	@Column
	private String issuerName;

	/**
	 * 交易地址
	 */
	@Column
	private String txnAddress;

	/**
	 * 交易唯一编号
	 */
	@Column
	private String txnId;

	/**
	 * 交易币种
	 */
	@Column
	private String salesCur;
	
	/**
	 * 是否允许退款
	 */
	@Column
	private Boolean isRefundEnable;
	
	/**
	 * 加密经度
	 */
	@Column
	public Double specLongitude;
	/**
	 * 加密纬度
	 */
	@Column
	public Double specLatitude;
	/**
	 * 坐标类型
	 */
	@Column
	public String specCoordType;
	/**
	 * 设备编号
	 */
	@Column
	public String deviceId;
	/**
	 * 更新时间 
	 */
	@Column
	public String updateTime;
	
	@Column
	private String origTxnId;
	
	
	/**
	 * 交易图片路径
	 */
	@Column
	private String tranPicPath;
	/**
	 * 电子签名图片路径
	 */
	@Column
	private String signPicPath;
	
	/**
	 * 云订单标示
	 */
	@Column
	private Boolean isCloudOrder;
	
//	/**
//	 * 输入模式
//	 */
//	@Column
//	private String serviceEntryMode;

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getExTraceNO() {
		return exTraceNO;
	}

	public void setExTraceNO(String exTraceNO) {
		this.exTraceNO = exTraceNO;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}



	public String getSalesCur() {
		return salesCur;
	}

	public void setSalesCur(String salesCur) {
		this.salesCur = salesCur;
	}


	public String getTermBatchNo() {
		return termBatchNo;
	}

	public void setTermBatchNo(String termBatchNo) {
		this.termBatchNo = termBatchNo;
	}

	public String getTermTraceNo() {
		return termTraceNo;
	}

	public void setTermTraceNo(String termTraceNo) {
		this.termTraceNo = termTraceNo;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getTermTxnTime() {
		return termTxnTime;
	}

	public void setTermTxnTime(String termTxnTime) {
		this.termTxnTime = termTxnTime;
	}

	public String getSubPartyNo() {
		return subPartyNo;
	}

	public void setSubPartyNo(String subPartyNo) {
		this.subPartyNo = subPartyNo;
	}

	public String getSubPartyName() {
		return subPartyName;
	}

	public void setSubPartyName(String subPartyName) {
		this.subPartyName = subPartyName;
	}

	public String getShortPan() {
		return shortPan;
	}

	public void setShortPan(String shortPan) {
		this.shortPan = shortPan;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getRefundAuditOper() {
		return refundAuditOper;
	}

	public void setRefundAuditOper(String refundAuditOper) {
		this.refundAuditOper = refundAuditOper;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getBankAuthNo() {
		return bankAuthNo;
	}

	public void setBankAuthNo(String bankAuthNo) {
		this.bankAuthNo = bankAuthNo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getHolPhoneNO() {
		return holPhoneNO;
	}

	public void setHolPhoneNO(String holPhoneNO) {
		this.holPhoneNO = holPhoneNO;
	}

	public String getHolEmail() {
		return holEmail;
	}

	public void setHolEmail(String holEmail) {
		this.holEmail = holEmail;
	}

	public String getHolMicroblog() {
		return holMicroblog;
	}

	public void setHolMicroblog(String holMicroblog) {
		this.holMicroblog = holMicroblog;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardOrg() {
		return cardOrg;
	}

	public void setCardOrg(String cardOrg) {
		this.cardOrg = cardOrg;
	}

	public String getTxnStatusDesc() {
		return txnStatusDesc;
	}

	public void setTxnStatusDesc(String txnStatusDesc) {
		this.txnStatusDesc = txnStatusDesc;
	}

	public String getTranPic() {
		return tranPic;
	}

	public void setTranPic(String tranPic) {
		this.tranPic = tranPic;
	}

	public String getSignPic() {
		return signPic;
	}

	public void setSignPic(String signPic) {
		this.signPic = signPic;
	}

	public String getPositionDesc() {
		return positionDesc;
	}

	public void setPositionDesc(String positionDesc) {
		this.positionDesc = positionDesc;
	}

	public String getTxnTypeDesc() {
		return txnTypeDesc;
	}

	public void setTxnTypeDesc(String txnTypeDesc) {
		this.txnTypeDesc = txnTypeDesc;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getOrigRefNo() {
		return origRefNo;
	}

	public void setOrigRefNo(String origRefNo) {
		this.origRefNo = origRefNo;
	}

	public Integer getIdTxn() {
		return idTxn;
	}

	public void setIdTxn(Integer idTxn) {
		this.idTxn = idTxn;
	}


	public String getIssuerId() {
		return issuerId;
	}

	public void setIssuerId(String issuerId) {
		this.issuerId = issuerId;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getTxnPartyId() {
		return txnPartyId;
	}

	public void setTxnPartyId(String txnPartyId) {
		this.txnPartyId = txnPartyId;
	}

	public String getTxnPartyName() {
		return txnPartyName;
	}

	public void setTxnPartyName(String txnPartyName) {
		this.txnPartyName = txnPartyName;
	}

	public String getTxnAddress() {
		return txnAddress;
	}

	public void setTxnAddress(String txnAddress) {
		this.txnAddress = txnAddress;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTxnFlag() {
		return txnFlag;
	}

	public void setTxnFlag(String txnFlag) {
		this.txnFlag = txnFlag;
	}

	public String getTxnFlagMessage() {
		return txnFlagMessage;
	}

	public void setTxnFlagMessage(String txnFlagMessage) {
		this.txnFlagMessage = txnFlagMessage;
	}

	public Boolean getIsRefundEnable() {
		return isRefundEnable;
	}

	public void setIsRefundEnable(Boolean isRefundEnable) {
		this.isRefundEnable = isRefundEnable;
	}

	public Double getSpecLongitude() {
		return specLongitude;
	}

	public void setSpecLongitude(Double specLongitude) {
		this.specLongitude = specLongitude;
	}

	public Double getSpecLatitude() {
		return specLatitude;
	}

	public void setSpecLatitude(Double specLatitude) {
		this.specLatitude = specLatitude;
	}

	public String getSpecCoordType() {
		return specCoordType;
	}

	public void setSpecCoordType(String specCoordType) {
		this.specCoordType = specCoordType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public BigDecimal getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

	public BigDecimal getRefundAmt() {
		return refundAmt;
	}

	public void setRefundAmt(BigDecimal refundAmt) {
		this.refundAmt = refundAmt;
	}

	public String getOrigTxnId() {
		return origTxnId;
	}

	public void setOrigTxnId(String origTxnId) {
		this.origTxnId = origTxnId;
	}

	public String getTranPicPath() {
		return tranPicPath;
	}

	public void setTranPicPath(String tranPicPath) {
		this.tranPicPath = tranPicPath;
	}

	public String getSignPicPath() {
		return signPicPath;
	}

	public void setSignPicPath(String signPicPath) {
		this.signPicPath = signPicPath;
	}

	public Boolean getIsCloudOrder() {
		return isCloudOrder;
	}

	public void setIsCloudOrder(Boolean isCloudOrder) {
		this.isCloudOrder = isCloudOrder;
	}
}
