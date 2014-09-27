package me.andpay.apos.tam.form;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import me.andpay.ac.term.api.txn.TxnResponse;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;

public class TxnActionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1974186602521355817L;

	// 输出数据

	private TxnResponse txnResponse;

	/**
	 * 交易地址
	 */
	private String txnAddress;

	/**
	 * 商品文件路径
	 */
	private String goodsFileURL;
	/**
	 * 交易编号
	 */
	private String txnId;

	/**
	 * 交易类型名称
	 */
	private String txnTypeName;

	/**
	 * 交易时间
	 */
	private Date txnTime;

	/**
	 * 结算时间
	 */
	private Date settlementTime;

	/**
	 * 密码错误计数器
	 */
	private int pinErrorCount;

	private String responMsg;

	/**
	 * 终端流水号
	 */
	private String termTraceNo;

	/**
	 * 终端交易时间
	 */
	private String termTxnTime;

	/**
	 * 短卡号
	 */
	private String shortCardNo;

	private BigDecimal balanceAmt;

	private AposICCardDataInfo aposICCardDataInfo;

	private boolean icCardTxn;

	/**
	 * 交易快照编号
	 */
	private int exPayInfoIdTxn;

	public int getExPayInfoIdTxn() {
		return exPayInfoIdTxn;
	}

	public void setExPayInfoIdTxn(int exPayInfoIdTxn) {
		this.exPayInfoIdTxn = exPayInfoIdTxn;
	}

	public AposICCardDataInfo getAposICCardDataInfo() {
		return aposICCardDataInfo;
	}

	public void setAposICCardDataInfo(AposICCardDataInfo aposICCardDataInfo) {
		this.aposICCardDataInfo = aposICCardDataInfo;
	}

	public BigDecimal getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(BigDecimal balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public String getShortCardNo() {
		return shortCardNo;
	}

	public void setShortCardNo(String shortCardNo) {
		this.shortCardNo = shortCardNo;
	}

	public TxnResponse getTxnResponse() {
		return txnResponse;
	}

	public void setTxnResponse(TxnResponse txnResponse) {
		this.txnResponse = txnResponse;
	}

	public String getTxnAddress() {
		return txnAddress;
	}

	public void setTxnAddress(String txnAddress) {
		this.txnAddress = txnAddress;
	}

	public String getGoodsFileURL() {
		return goodsFileURL;
	}

	public void setGoodsFileURL(String goodsFileURL) {
		this.goodsFileURL = goodsFileURL;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getTxnTypeName() {
		return txnTypeName;
	}

	public void setTxnTypeName(String txnTypeName) {
		this.txnTypeName = txnTypeName;
	}

	public Date getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(Date txnTime) {
		this.txnTime = txnTime;
	}

	public int getPinErrorCount() {
		return pinErrorCount;
	}

	public void setPinErrorCount(int pinErrorCount) {
		this.pinErrorCount = pinErrorCount;
	}

	public void setResponMsg(String responMsg) {
		this.responMsg = responMsg;
	}

	public String getResponMsg() {
		return responMsg;
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

	public Date getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Date settlementTime) {
		this.settlementTime = settlementTime;
	}

	public boolean isIcCardTxn() {
		return icCardTxn;
	}

	public void setIcCardTxn(boolean icCardTxn) {
		this.icCardTxn = icCardTxn;
	}

}
