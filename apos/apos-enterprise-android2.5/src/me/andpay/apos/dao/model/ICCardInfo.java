package me.andpay.apos.dao.model;

import me.andpay.timobileframework.sqlite.anno.Column;
import me.andpay.timobileframework.sqlite.anno.ID;
import me.andpay.timobileframework.sqlite.anno.TableName;

@TableName(name = "ICCardInfo", version = 1)
public class ICCardInfo {
	/**
	 * 主键编号
	 */
	@ID
	@Column
	private Integer idICCardInfo;

	@Column
	private String appCryptogram;
	@Column
	private String cryptogramInfoData;
	@Column
	private String issuerAppData;
	@Column
	private String unpredictableNumber;
	@Column
	private String appTxnCounter;
	@Column
	private String termVerificationResult;
	@Column
	private String txnDate;
	@Column
	private String txnType;
	@Column
	private String txnAmtOrAuthAmt;
	@Column
	private String txnCurrCode;
	@Column
	private String appInterProfile;
	@Column
	private String termCountryCode;
	@Column
	private String amtOther;
	@Column
	private String termCapabilities;
	@Column
	private String eCashIssuerAuthCode;
	@Column
	private String holerVeriMethodResult;
	@Column
	private String termType;
	@Column
	private String interfaceDeviceSeNo;
	@Column
	private String dedicatedFileName;
	@Column
	private String termAppVerNO;
	@Column
	private String txnSequenceCounter;
	@Column
	private String issuerAuthData;
	@Column
	private String issuerScriptTem1;
	@Column
	private String issuerScriptTem2;
	@Column
	private String issuerScriptResult;
	@Column
	private String cardProductID;
	@Column
	private Integer idTxn;
	@Column
	private String cardSerialNumber;

	public String getCardSerialNumber() {
		return cardSerialNumber;
	}

	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}

	public Integer getIdICCardInfo() {
		return idICCardInfo;
	}

	public void setIdICCardInfo(Integer idICCardInfo) {
		this.idICCardInfo = idICCardInfo;
	}

	public String getAppCryptogram() {
		return appCryptogram;
	}

	public void setAppCryptogram(String appCryptogram) {
		this.appCryptogram = appCryptogram;
	}

	public String getCryptogramInfoData() {
		return cryptogramInfoData;
	}

	public void setCryptogramInfoData(String cryptogramInfoData) {
		this.cryptogramInfoData = cryptogramInfoData;
	}

	public String getIssuerAppData() {
		return issuerAppData;
	}

	public void setIssuerAppData(String issuerAppData) {
		this.issuerAppData = issuerAppData;
	}

	public String getUnpredictableNumber() {
		return unpredictableNumber;
	}

	public void setUnpredictableNumber(String unpredictableNumber) {
		this.unpredictableNumber = unpredictableNumber;
	}

	public String getAppTxnCounter() {
		return appTxnCounter;
	}

	public void setAppTxnCounter(String appTxnCounter) {
		this.appTxnCounter = appTxnCounter;
	}

	public String getTermVerificationResult() {
		return termVerificationResult;
	}

	public void setTermVerificationResult(String termVerificationResult) {
		this.termVerificationResult = termVerificationResult;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getTxnAmtOrAuthAmt() {
		return txnAmtOrAuthAmt;
	}

	public void setTxnAmtOrAuthAmt(String txnAmtOrAuthAmt) {
		this.txnAmtOrAuthAmt = txnAmtOrAuthAmt;
	}

	public String getTxnCurrCode() {
		return txnCurrCode;
	}

	public void setTxnCurrCode(String txnCurrCode) {
		this.txnCurrCode = txnCurrCode;
	}

	public String getAppInterProfile() {
		return appInterProfile;
	}

	public void setAppInterProfile(String appInterProfile) {
		this.appInterProfile = appInterProfile;
	}

	public String getTermCountryCode() {
		return termCountryCode;
	}

	public void setTermCountryCode(String termCountryCode) {
		this.termCountryCode = termCountryCode;
	}

	public String getAmtOther() {
		return amtOther;
	}

	public void setAmtOther(String amtOther) {
		this.amtOther = amtOther;
	}

	public String getTermCapabilities() {
		return termCapabilities;
	}

	public void setTermCapabilities(String termCapabilities) {
		this.termCapabilities = termCapabilities;
	}

	public String geteCashIssuerAuthCode() {
		return eCashIssuerAuthCode;
	}

	public void seteCashIssuerAuthCode(String eCashIssuerAuthCode) {
		this.eCashIssuerAuthCode = eCashIssuerAuthCode;
	}

	public String getHolerVeriMethodResult() {
		return holerVeriMethodResult;
	}

	public void setHolerVeriMethodResult(String holerVeriMethodResult) {
		this.holerVeriMethodResult = holerVeriMethodResult;
	}

	public String getTermType() {
		return termType;
	}

	public void setTermType(String termType) {
		this.termType = termType;
	}

	public String getInterfaceDeviceSeNo() {
		return interfaceDeviceSeNo;
	}

	public void setInterfaceDeviceSeNo(String interfaceDeviceSeNo) {
		this.interfaceDeviceSeNo = interfaceDeviceSeNo;
	}

	public String getDedicatedFileName() {
		return dedicatedFileName;
	}

	public void setDedicatedFileName(String dedicatedFileName) {
		this.dedicatedFileName = dedicatedFileName;
	}

	public String getTermAppVerNO() {
		return termAppVerNO;
	}

	public void setTermAppVerNO(String termAppVerNO) {
		this.termAppVerNO = termAppVerNO;
	}

	public String getTxnSequenceCounter() {
		return txnSequenceCounter;
	}

	public void setTxnSequenceCounter(String txnSequenceCounter) {
		this.txnSequenceCounter = txnSequenceCounter;
	}

	public String getIssuerAuthData() {
		return issuerAuthData;
	}

	public void setIssuerAuthData(String issuerAuthData) {
		this.issuerAuthData = issuerAuthData;
	}

	public String getIssuerScriptTem1() {
		return issuerScriptTem1;
	}

	public void setIssuerScriptTem1(String issuerScriptTem1) {
		this.issuerScriptTem1 = issuerScriptTem1;
	}

	public String getIssuerScriptTem2() {
		return issuerScriptTem2;
	}

	public void setIssuerScriptTem2(String issuerScriptTem2) {
		this.issuerScriptTem2 = issuerScriptTem2;
	}

	public String getIssuerScriptResult() {
		return issuerScriptResult;
	}

	public void setIssuerScriptResult(String issuerScriptResult) {
		this.issuerScriptResult = issuerScriptResult;
	}

	public String getCardProductID() {
		return cardProductID;
	}

	public void setCardProductID(String cardProductID) {
		this.cardProductID = cardProductID;
	}

	public Integer getIdTxn() {
		return idTxn;
	}

	public void setIdTxn(Integer idTxn) {
		this.idTxn = idTxn;
	}

}
