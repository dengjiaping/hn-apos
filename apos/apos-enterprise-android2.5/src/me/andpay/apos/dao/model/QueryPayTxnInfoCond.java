package me.andpay.apos.dao.model;

import java.math.BigDecimal;

import me.andpay.timobileframework.sqlite.Sorts;
import me.andpay.timobileframework.sqlite.anno.Expression;
import me.andpay.timobileframework.sqlite.convert.BigDecimalConverter;

public class QueryPayTxnInfoCond extends Sorts {
	/**
	 * 本地系统参考号
	 */
	@Expression
	private Integer idTxn;
	/**
	 * 外部跟踪编号
	 */
	@Expression
	private String exTraceNO;
	/**
	 * 交易状态
	 */
	@Expression
	private String txnStatus;
	/**
	 * 交易状态
	 */
	@Expression
	private String txnId;

	/**
	 * 系统参考号
	 */
	@Expression
	private String refNo;
	/**
	 * 交易开始事件
	 */
	@Expression(paraName = "termTxnTime", operater = ">=")
	private String beginTermTxnTime;
	/**
	 * 交易结束时间
	 */
	@Expression(paraName = "termTxnTime", operater = "<=")
	private String endTermTxnTime;
	/**
	 * 最大refno
	 */
	@Expression(paraName = "txnId", operater = "<")
	private String maxTxnId;
	/**
	 * 最小refno
	 */
	@Expression(paraName = "txnId", operater = ">")
	private String minTxnId;

	@Expression
	private String txnType;

	@Expression(dataConverter = BigDecimalConverter.class)
	private BigDecimal salesAmt;

	@Expression
	private String termTraceNo;
	/**
	 * 交易时间
	 */
	@Expression
	private String txnTime;
	/**
	 * 终端交易时间
	 */
	@Expression
	private String termTxnTime;
	/**
	 * 商户id
	 */
	@Expression
	private String txnPartyId;
	/**
	 * 用户id
	 */
	@Expression
	private String operNo;

	@Expression
	private String shortPan;

	@Expression
	private Boolean isRefundEnable;

	/**
	 * 开始更新时间
	 */
	@Expression(paraName = "updateTime", operater = ">=")
	public String beginUpdateTime;
	/**
	 * 结束更新时间
	 */
	@Expression(paraName = "updateTime", operater = "<")
	public String endUpdateTime;

	public String getShortPan() {
		return shortPan;
	}

	public void setShortPan(String shortPan) {
		this.shortPan = shortPan;
	}

	public Integer getIdTxn() {
		return idTxn;
	}

	public void setIdTxn(Integer idTxn) {
		this.idTxn = idTxn;
	}

	public String getExTraceNO() {
		return exTraceNO;
	}

	public void setExTraceNO(String exTraceNO) {
		this.exTraceNO = exTraceNO;
	}

	public String getTxnStatus() {
		return txnStatus;
	}

	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}

	public String getBeginTermTxnTime() {
		return beginTermTxnTime;
	}

	public void setBeginTermTxnTime(String beginTermTxnTime) {
		this.beginTermTxnTime = beginTermTxnTime;
	}

	public String getEndTermTxnTime() {
		return endTermTxnTime;
	}

	public void setEndTermTxnTime(String endTermTxnTime) {
		this.endTermTxnTime = endTermTxnTime;
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

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTermTraceNo() {
		return termTraceNo;
	}

	public void setTermTraceNo(String termTraceNo) {
		this.termTraceNo = termTraceNo;
	}

	public String getTermTxnTime() {
		return termTxnTime;
	}

	public void setTermTxnTime(String termTxnTime) {
		this.termTxnTime = termTxnTime;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getTxnPartyId() {
		return txnPartyId;
	}

	public void setTxnPartyId(String txnPartyId) {
		this.txnPartyId = txnPartyId;
	}

	public String getOperNo() {
		return operNo;
	}

	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}

	public Boolean getIsRefundEnable() {
		return isRefundEnable;
	}

	public void setIsRefundEnable(Boolean isRefundEnable) {
		this.isRefundEnable = isRefundEnable;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getBeginUpdateTime() {
		return beginUpdateTime;
	}

	public void setBeginUpdateTime(String beginUpdateTime) {
		this.beginUpdateTime = beginUpdateTime;
	}

	public String getEndUpdateTime() {
		return endUpdateTime;
	}

	public void setEndUpdateTime(String endUpdateTime) {
		this.endUpdateTime = endUpdateTime;
	}

	public BigDecimal getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

}
