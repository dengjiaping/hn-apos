package me.andpay.apos.tqm.form;

import me.andpay.apos.R;
import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.annotation.IsConst;
import me.andpay.timobileframework.mvc.form.annotation.ParamId;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;
import me.andpay.timobileframework.util.DateUtil;

@FormInfo(action = "/tqm/query.action", domain = "queryTxnList", formName = "QueryConditionForm")
public class QueryConditionForm {
	/**
	 * 日期开始时间
	 */
	@ParamId(R.id.tqm_txn_list_condition_date_begin_ev)
	@FieldValidate.MASK(pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String beginDate;
	/**
	 * 日期结束时间
	 */
	@ParamId(R.id.tqm_txn_list_condition_date_end_ev)
	@FieldValidate.MASK(pattern = "[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String endDate;
	/**
	 * 开始时间
	 */
	@IsConst
	private String beginTime = DateUtil.BEGINTIMES;
	/**
	 * 结束时间
	 */
	@IsConst
	private String endTime = DateUtil.ENDTIMES;

	/**
	 * 订单编号
	 */
	@ParamId(R.id.tqm_txn_list_condition_orderid_ev)
	@FieldValidate.COMMMONCHAR
	private String orderno;
	/**
	 * 状态
	 */
	@IsConst
	private String status;
	/**
	 * 最大编号
	 */
	@IsConst
	private String maxTxnId;

	/**
	 * 最小编号
	 */
	@IsConst
	private String minTxnId;
	/**
	 * 最小编号
	 */
	@ParamId(R.id.tqm_txn_list_condition_refno_ev)
	private String txnId;
	/**
	 * 交易类型
	 */
	@ParamId(R.id.tqm_txn_list_condition_txntype_sp)
	private String txnType;

	@ParamId(R.id.tqm_txn_list_condition_cardno_tv)
	private String cardno;

	@IsConst
	private String txnPartyId;

	@IsConst
	private String userId;

	@IsConst
	private Boolean isRefundEnableFlag;

	@ParamId(R.id.tqm_txn_list_condition_amount_ev)
	@FieldValidate.DOUBLE
	private String amount;
	
	/**
	 * 界面是否有条件
	 */
	private boolean hasViewCond;
	
	private Long settleOrderId;

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
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

	public String getCardno() {
		return cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}

	public String getTxnPartyId() {
		return txnPartyId;
	}

	public void setTxnPartyId(String txnPartyId) {
		this.txnPartyId = txnPartyId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public Boolean isRefundEnableFlag() {
		return isRefundEnableFlag;
	}

	public void setRefundEnableFlag(Boolean isRefundEnableFlag) {
		this.isRefundEnableFlag = isRefundEnableFlag;
	}

	public boolean isHasViewCond() {
		return hasViewCond;
	}

	public void setHasViewCond(boolean hasViewCond) {
		this.hasViewCond = hasViewCond;
	}

	public Long getSettleOrderId() {
		return settleOrderId;
	}

	public void setSettleOrderId(Long settleOrderId) {
		this.settleOrderId = settleOrderId;
	}
	
	

}
