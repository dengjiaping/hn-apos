package me.andpay.util;

import me.andpay.timobileframework.util.tlv.TlvField;

public class AposICCardDataInfo {

	

	@TlvField(value = "9F26", index = 1)
	private String appCryptogram;
	@TlvField(value = "9F27", index = 2)
	private String cryptogramInfoData;
	@TlvField(value = "9F10", index = 3)
	private String issuerAppData;
	@TlvField(value = "9F37", index = 4)
	private String unpredictableNumber;
	@TlvField(value = "9F36", index = 5)
	private String appTxnCounter;
	@TlvField(value = "95", index = 6)
	private String termVerificationResult;
	@TlvField(value = "9A", index = 7)
	private String txnDate;
	@TlvField(value = "9C", index = 8)
	private String txnType;
	@TlvField(value = "9F02", index = 9)
	private String txnAmtOrAuthAmt;
	@TlvField(value = "5F2A", index = 10)
	private String txnCurrCode;
	@TlvField(value = "82", index = 11)
	private String appInterProfile;
	@TlvField(value = "9F1A", index = 12)
	private String termCountryCode;
	@TlvField(value = "9F03", index = 13)
	private String amtOther;
	@TlvField(value = "9F33", index = 14)
	private String termCapabilities;
	@TlvField(value = "9F74", index = 15)
	private String eCashIssuerAuthCode;
	@TlvField(value = "9F34", index = 16)
	private String holerVeriMethodResult;
	@TlvField(value = "9F35", index = 17)
	private String termType;
	@TlvField(value = "9F1E", index = 18)
	private String interfaceDeviceSeNo;
	@TlvField(value = "84", index = 19)
	private String dedicatedFileName;
	@TlvField(value = "9F09", index = 20)
	private String termAppVerNO;
	@TlvField(value = "9F41", index = 21)
	private String txnSequenceCounter;
	@TlvField(value = "91", index =22)
	private String issuerAuthData;
	@TlvField(value = "71", index = 23)
	private String issuerScriptTem1;
	@TlvField(value = "72", index = 24)
	private String issuerScriptTem2;
	@TlvField(value = "DF31", index = 25)
	private String issuerScriptResult;
	@TlvField(value = "9F63", index = 26)
	private String cardProductID;
	
	//======其他附加数据=====
	
	private String terminalTraceNo;

	private String ksn;

	private String pinData;

	private String cardNo;
	
	private String autoCode;
	
	private String cardSerialNumber;

	
	public String getCardSerialNumber() {
		return cardSerialNumber;
	}

	public void setCardSerialNumber(String cardSerialNumber) {
		this.cardSerialNumber = cardSerialNumber;
	}

	public String getAutoCode() {
		return autoCode;
	}

	public void setAutoCode(String autoCode) {
		this.autoCode = autoCode;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public String getPinData() {
		return pinData;
	}

	public void setPinData(String pinData) {
		this.pinData = pinData;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTerminalTraceNo() {
		return terminalTraceNo;
	}

	public void setTerminalTraceNo(String terminalTraceNo) {
		this.terminalTraceNo = terminalTraceNo;
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

}
