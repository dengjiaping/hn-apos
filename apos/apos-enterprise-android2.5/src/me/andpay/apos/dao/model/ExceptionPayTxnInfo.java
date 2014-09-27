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
@TableName(name = "ExceptionPayTxnInfo", version = 6)
public class ExceptionPayTxnInfo implements Serializable {
	/**
	 * 异常交易，等待重试
	 */
	public static final String EXP_STATUS_WAIT_RETRY = "0";

	/**
	 * 等待签名
	 */
	public static final String EXP_STATUS_WAIT_SIGN = "1";
	/**
	 * 等待冲正交易
	 */
	public static final String EXP_STATUS_WAIT_REVERSE = "2";
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
	 * 异常交易状态
	 */
	@Column
	private String expcetionStatus;

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
	 * 结账标志
	 */
	@Column
	private Boolean stlFlag = false;
	/**
	 * 退货标志
	 */
	@Column
	private Boolean refundFlag = false;
	/**
	 * 撤销标志
	 */
	@Column
	private Boolean voidFlag = false;
	/**
	 * 金额
	 */
	@Column(dataConverter = BigDecimalConverter.class)
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
	 * 扩展字段1
	 */
	@Column
	private String ext1;
	/**
	 * 扩展字段2
	 */
	@Column
	private String ext2;
	/**
	 * 扩展字段3
	 */
	@Column
	private String ext3;
	/**
	 * 扩展字段4
	 */
	@Column
	private String ext4;
	/**
	 * 扩展字段5
	 */
	@Column
	private String ext5;
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
	@Column(dataConverter = BigDecimalConverter.class)
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
	 * 全磁道
	 */
	@Column
	private String trackAll;
	/**
	 * 加密要素
	 */
	@Column
	private String trackRandomFactor;

	/**
	 * 交易币种
	 */
	@Column
	private String salesCur;

	/**
	 * 原交易编号
	 */
	@Column
	private String origTxnId;

	/**
	 * 交易状态标志
	 */
	@Column
	private String txnFlag;

	/**
	 * 是否允许退款
	 */
	@Column
	private Boolean isRefundEnable;
	/**
	 * 加密经度
	 */
	@Column
	private Double specLongitude;
	/**
	 * 加密纬度
	 */
	@Column
	private Double specLatitude;
	/**
	 * 坐标类型
	 */
	@Column
	private String specCoordType;
	/**
	 * 设备编号
	 */
	@Column
	private String deviceId;

	/**
	 * 是否是IC卡交易
	 */
	@Column
	private Boolean isICCardTxn;

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

	public Boolean getIsRefundEnable() {
		return isRefundEnable;
	}

	public void setIsRefundEnable(Boolean isRefundEnable) {
		this.isRefundEnable = isRefundEnable;
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

	public Boolean getStlFlag() {
		return stlFlag;
	}

	public void setStlFlag(Boolean stlFlag) {
		this.stlFlag = stlFlag;
	}

	public Boolean getRefundFlag() {
		return refundFlag;
	}

	public void setRefundFlag(Boolean refundFlag) {
		this.refundFlag = refundFlag;
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

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
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

	public Boolean getVoidFlag() {
		return voidFlag;
	}

	public void setVoidFlag(Boolean voidFlag) {
		this.voidFlag = voidFlag;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTrackAll() {
		return trackAll;
	}

	public void setTrackAll(String trackAll) {
		this.trackAll = trackAll;
	}

	public String getTrackRandomFactor() {
		return trackRandomFactor;
	}

	public void setTrackRandomFactor(String trackRandomFactor) {
		this.trackRandomFactor = trackRandomFactor;
	}

	public String getExpcetionStatus() {
		return expcetionStatus;
	}

	public void setExpcetionStatus(String expcetionStatus) {
		this.expcetionStatus = expcetionStatus;
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

	public String getTxnFlag() {
		return txnFlag;
	}

	public void setTxnFlag(String txnFlag) {
		this.txnFlag = txnFlag;
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

	public Boolean getIsICCardTxn() {
		if (isICCardTxn == null) {
			return false;
		}
		return isICCardTxn;
	}

	public void setIsICCardTxn(Boolean isICCardTxn) {
		this.isICCardTxn = isICCardTxn;
	}

}
