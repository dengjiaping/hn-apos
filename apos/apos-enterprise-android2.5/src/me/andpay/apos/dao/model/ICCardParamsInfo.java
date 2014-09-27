package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;

@TableName(name = "ICCardParamsInfo", version = 1)
public class ICCardParamsInfo {

	/**
	 * 主键编号
	 */
	@ID
	@Column
	private Integer idICCardParamsInfo;
	@Column
	private String aid;
	@Column
	private String ais;
	@Column
	private String appViersionId;
	@Column
	private String tacDefault;
	@Column
	private String tacOnline;
	@Column
	private String tacReject;
	@Column
	private String terminalFloorLimit;
	@Column
	private String thresholdBias;
	@Column
	private String maxTargetPercentageBias;
	@Column
	private String targetPercentage;
	@Column
	private String defaultDool;
	@Column
	private String terminalOnlinePinBlance;
	@Column
	private String terminalEcashTxnQuota;
	@Column
	private String contactlessReaderOfflineMinQuoata;
	@Column
	private String conteactLessReaderTxnQuota;
	@Column
	private String readerCvmLimit;

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getAis() {
		return ais;
	}

	public void setAis(String ais) {
		this.ais = ais;
	}

	public String getAppViersionId() {
		return appViersionId;
	}

	public void setAppViersionId(String appViersionId) {
		this.appViersionId = appViersionId;
	}

	public String getTacDefault() {
		return tacDefault;
	}

	public void setTacDefault(String tacDefault) {
		this.tacDefault = tacDefault;
	}

	public String getTacOnline() {
		return tacOnline;
	}

	public void setTacOnline(String tacOnline) {
		this.tacOnline = tacOnline;
	}

	public String getTacReject() {
		return tacReject;
	}

	public void setTacReject(String tacReject) {
		this.tacReject = tacReject;
	}

	public String getTerminalFloorLimit() {
		return terminalFloorLimit;
	}

	public void setTerminalFloorLimit(String terminalFloorLimit) {
		this.terminalFloorLimit = terminalFloorLimit;
	}

	public String getThresholdBias() {
		return thresholdBias;
	}

	public void setThresholdBias(String thresholdBias) {
		this.thresholdBias = thresholdBias;
	}

	public String getMaxTargetPercentageBias() {
		return maxTargetPercentageBias;
	}

	public void setMaxTargetPercentageBias(String maxTargetPercentageBias) {
		this.maxTargetPercentageBias = maxTargetPercentageBias;
	}

	public String getTargetPercentage() {
		return targetPercentage;
	}

	public void setTargetPercentage(String targetPercentage) {
		this.targetPercentage = targetPercentage;
	}

	public String getDefaultDool() {
		return defaultDool;
	}

	public void setDefaultDool(String defaultDool) {
		this.defaultDool = defaultDool;
	}

	public String getTerminalOnlinePinBlance() {
		return terminalOnlinePinBlance;
	}

	public void setTerminalOnlinePinBlance(String terminalOnlinePinBlance) {
		this.terminalOnlinePinBlance = terminalOnlinePinBlance;
	}

	public String getTerminalEcashTxnQuota() {
		return terminalEcashTxnQuota;
	}

	public void setTerminalEcashTxnQuota(String terminalEcashTxnQuota) {
		this.terminalEcashTxnQuota = terminalEcashTxnQuota;
	}

	public String getContactlessReaderOfflineMinQuoata() {
		return contactlessReaderOfflineMinQuoata;
	}

	public void setContactlessReaderOfflineMinQuoata(
			String contactlessReaderOfflineMinQuoata) {
		this.contactlessReaderOfflineMinQuoata = contactlessReaderOfflineMinQuoata;
	}

	public String getConteactLessReaderTxnQuota() {
		return conteactLessReaderTxnQuota;
	}

	public void setConteactLessReaderTxnQuota(String conteactLessReaderTxnQuota) {
		this.conteactLessReaderTxnQuota = conteactLessReaderTxnQuota;
	}

	public String getReaderCvmLimit() {
		return readerCvmLimit;
	}

	public void setReaderCvmLimit(String readerCvmLimit) {
		this.readerCvmLimit = readerCvmLimit;
	}

	public Integer getIdICCardParamsInfo() {
		return idICCardParamsInfo;
	}

	public void setIdICCardParamsInfo(Integer idICCardParamsInfo) {
		this.idICCardParamsInfo = idICCardParamsInfo;
	}

}
