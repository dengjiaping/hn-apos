package me.andpay.apos.cdriver.model;

import me.andpay.timobileframework.util.tlv.ConvertData;
import me.andpay.timobileframework.util.tlv.TlvField;
import me.andpay.timobileframework.util.tlv.convert.ByteToHexConvert;

public class AposICCardDataInfo {

	

	@TlvField(value = "9F26", index = 1)
	@ConvertData(value=ByteToHexConvert.class)
	private String appCryptogram;
	@TlvField(value = "9F27", index = 2)
	@ConvertData(value=ByteToHexConvert.class)
	private String cryptogramInfoData;
	@TlvField(value = "9F10", index = 3)
	@ConvertData(value=ByteToHexConvert.class)
	private String issuerAppData;
	@TlvField(value = "9F37", index = 4)
	@ConvertData(value=ByteToHexConvert.class)
	private String unpredictableNumber;
	@TlvField(value = "9F36", index = 5)
	@ConvertData(value=ByteToHexConvert.class)
	private String appTxnCounter;
	@TlvField(value = "95", index = 6)
	@ConvertData(value=ByteToHexConvert.class)
	private String termVerificationResult;
	@TlvField(value = "9A", index = 7)
	@ConvertData(value=ByteToHexConvert.class)
	private String txnDate;
	@TlvField(value = "9C", index = 8)
	@ConvertData(value=ByteToHexConvert.class)
	private String txnType;
	@TlvField(value = "9F02", index = 9)
	@ConvertData(value=ByteToHexConvert.class)
	private String txnAmtOrAuthAmt;
	@TlvField(value = "5F2A", index = 10)
	@ConvertData(value=ByteToHexConvert.class)
	private String txnCurrCode;
	@TlvField(value = "82", index = 11)
	@ConvertData(value=ByteToHexConvert.class)
	private String appInterProfile;
	@TlvField(value = "9F1A", index = 12)
	@ConvertData(value=ByteToHexConvert.class)
	private String termCountryCode;
	@TlvField(value = "9F03", index = 13)
	@ConvertData(value=ByteToHexConvert.class)
	private String amtOther;
	@TlvField(value = "9F33", index = 14)
	@ConvertData(value=ByteToHexConvert.class)
	private String termCapabilities;
	@TlvField(value = "9F74", index = 15)
	@ConvertData(value=ByteToHexConvert.class)
	private String eCashIssuerAuthCode;
	@TlvField(value = "9F34", index = 16)
	@ConvertData(value=ByteToHexConvert.class)
	private String holerVeriMethodResult;
	@TlvField(value = "9F35", index = 17)
	@ConvertData(value=ByteToHexConvert.class)
	private String termType;
	@TlvField(value = "9F1E", index = 18)
	@ConvertData(value=ByteToHexConvert.class)
	private String interfaceDeviceSeNo;
	@TlvField(value = "84", index = 19)
	@ConvertData(value=ByteToHexConvert.class)
	private String dedicatedFileName;
	@TlvField(value = "9F09", index = 20)
	@ConvertData(value=ByteToHexConvert.class)
	private String termAppVerNO;
	@TlvField(value = "9F41", index = 21)
	@ConvertData(value=ByteToHexConvert.class)
	private String txnSequenceCounter;
	@TlvField(value = "91", index =22)
	@ConvertData(value=ByteToHexConvert.class)
	private String issuerAuthData;
	@TlvField(value = "71", index = 23)
	@ConvertData(value=ByteToHexConvert.class)
	private String issuerScriptTem1;
	@TlvField(value = "72", index = 24)
	@ConvertData(value=ByteToHexConvert.class)
	private String issuerScriptTem2;
	@TlvField(value = "DF31", index = 25)
	@ConvertData(value=ByteToHexConvert.class)
	private String issuerScriptResult;
	@TlvField(value = "9F63", index = 26)
	@ConvertData(value=ByteToHexConvert.class)
	private String cardProductID;
	
	//======其他附加数据=====
	
	private String terminalTraceNo;

	private String ksn;

	private String pinData;

	private String cardNo;
	
	private String autoCode;
	
	private String cardSerialNumber;
	
	private String trackAll;
	
	private String bcdFillZero(String data) {
		
		return data;
	}

	
	public String getCardSerialNumber() {
		return bcdFillZero(cardSerialNumber);
	}

	public void setCardSerialNumber(String cardSerialNumber) {

		this.cardSerialNumber = cardSerialNumber;
	}

	public String getAutoCode() {
		return  bcdFillZero(autoCode);
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
		return  bcdFillZero(appCryptogram);
	}

	public void setAppCryptogram(String appCryptogram) {
		if(appCryptogram!= null && appCryptogram.length()%2 !=0) {
			appCryptogram = "0"+appCryptogram;
		}
		this.appCryptogram = appCryptogram;
	}

	public String getCryptogramInfoData() {
		return  bcdFillZero(cryptogramInfoData);
	}

	public void setCryptogramInfoData(String cryptogramInfoData) {
		if(cryptogramInfoData!= null && cryptogramInfoData.length()%2 !=0) {
			cryptogramInfoData = "0"+cryptogramInfoData;
		}
		this.cryptogramInfoData = cryptogramInfoData;
	}

	public String getIssuerAppData() {
		return  bcdFillZero(issuerAppData);
	}

	public void setIssuerAppData(String issuerAppData) {
		if(issuerAppData!= null && issuerAppData.length()%2 !=0) {
			issuerAppData = "0"+issuerAppData;
		}
		this.issuerAppData = issuerAppData;
	}

	public String getUnpredictableNumber() {
		return  bcdFillZero(unpredictableNumber);
	}

	public void setUnpredictableNumber(String unpredictableNumber) {
		if(unpredictableNumber!= null && unpredictableNumber.length()%2 !=0) {
			unpredictableNumber = "0"+unpredictableNumber;
		}
		this.unpredictableNumber = unpredictableNumber;
	}

	public String getAppTxnCounter() {
		return  bcdFillZero(appTxnCounter);
	}

	public void setAppTxnCounter(String appTxnCounter) {
		if(appTxnCounter!= null && appTxnCounter.length()%2 !=0) {
			appTxnCounter = "0"+appTxnCounter;
		}
		this.appTxnCounter = appTxnCounter;
	}

	public String getTermVerificationResult() {
		return  bcdFillZero(termVerificationResult);
	}

	public void setTermVerificationResult(String termVerificationResult) {
		if(termVerificationResult!= null && termVerificationResult.length()%2 !=0) {
			termVerificationResult = "0"+termVerificationResult;
		}
		this.termVerificationResult = termVerificationResult;
	}

	public String getTxnDate() {
		
		return  bcdFillZero(txnDate);
	}

	public void setTxnDate(String txnDate) {
		if(txnDate!= null && txnDate.length()%2 !=0) {
			txnDate = "0"+txnDate;
		}
		this.txnDate = txnDate;
	}

	public String getTxnType() {
		
		return  bcdFillZero(txnType);
	}

	public void setTxnType(String txnType) {
		if(txnType!= null && txnType.length()%2 !=0) {
			txnType = "0"+txnType;
		}
		this.txnType = txnType;
	}

	public String getTxnAmtOrAuthAmt() {
		return  bcdFillZero(txnAmtOrAuthAmt);
	}

	public void setTxnAmtOrAuthAmt(String txnAmtOrAuthAmt) {
		if(txnAmtOrAuthAmt!= null && txnAmtOrAuthAmt.length()%2 !=0) {
			txnAmtOrAuthAmt = "0"+txnAmtOrAuthAmt;
		}
		this.txnAmtOrAuthAmt = txnAmtOrAuthAmt;
	}

	public String getTxnCurrCode() {
		return  bcdFillZero(txnCurrCode);
	}

	public void setTxnCurrCode(String txnCurrCode) {
		if(txnCurrCode!= null && txnCurrCode.length()%2 !=0) {
			txnCurrCode = "0"+txnCurrCode;
		}
		this.txnCurrCode = txnCurrCode;
	}

	public String getAppInterProfile() {
		return  bcdFillZero(appInterProfile);
	}

	public void setAppInterProfile(String appInterProfile) {
		if(appInterProfile!= null && appInterProfile.length()%2 !=0) {
			appInterProfile = "0"+appInterProfile;
		}
		this.appInterProfile = appInterProfile;
	}

	public String getTermCountryCode() {
		return  bcdFillZero(termCountryCode);
	}

	public void setTermCountryCode(String termCountryCode) {
		if(termCountryCode!= null && termCountryCode.length()%2 !=0) {
			termCountryCode = "0"+termCountryCode;
		}
		this.termCountryCode = termCountryCode;
	}

	public String getAmtOther() {
		return  bcdFillZero(amtOther);
	}

	public void setAmtOther(String amtOther) {
		if(amtOther!= null && amtOther.length()%2 !=0) {
			amtOther = "0"+amtOther;
		}
		this.amtOther = amtOther;
	}

	public String getTermCapabilities() {
		return  bcdFillZero(termCapabilities);
	}

	public void setTermCapabilities(String termCapabilities) {
		if(termCapabilities!= null && termCapabilities.length()%2 !=0) {
			termCapabilities = "0"+termCapabilities;
		}
		this.termCapabilities = termCapabilities;
	}

	public String geteCashIssuerAuthCode() {
		return  bcdFillZero(eCashIssuerAuthCode);
	}

	public void seteCashIssuerAuthCode(String eCashIssuerAuthCode) {
		if(eCashIssuerAuthCode!= null && eCashIssuerAuthCode.length()%2 !=0) {
			eCashIssuerAuthCode = "0"+eCashIssuerAuthCode;
		}
		this.eCashIssuerAuthCode = eCashIssuerAuthCode;
	}

	public String getHolerVeriMethodResult() {
		return  bcdFillZero(holerVeriMethodResult);
	}

	public void setHolerVeriMethodResult(String holerVeriMethodResult) {
		if(holerVeriMethodResult!= null && holerVeriMethodResult.length()%2 !=0) {
			holerVeriMethodResult = "0"+holerVeriMethodResult;
		}
		this.holerVeriMethodResult = holerVeriMethodResult;
	}

	public String getTermType() {
		return  bcdFillZero(termType);
	}

	public void setTermType(String termType) {
		if(termType!= null && termType.length()%2 !=0) {
			termType = "0"+termType;
		}
		this.termType = termType;
	}

	public String getInterfaceDeviceSeNo() {
		return  bcdFillZero(interfaceDeviceSeNo);
	}

	public void setInterfaceDeviceSeNo(String interfaceDeviceSeNo) {
		if(interfaceDeviceSeNo!= null && interfaceDeviceSeNo.length()%2 !=0) {
			interfaceDeviceSeNo = "0"+interfaceDeviceSeNo;
		}
		this.interfaceDeviceSeNo = interfaceDeviceSeNo;
	}

	public String getDedicatedFileName() {
		return  bcdFillZero(dedicatedFileName);
	}

	public void setDedicatedFileName(String dedicatedFileName) {
		if(dedicatedFileName!= null && dedicatedFileName.length()%2 !=0) {
			dedicatedFileName = "0"+dedicatedFileName;
		}
		this.dedicatedFileName = dedicatedFileName;
	}

	public String getTermAppVerNO() {
		return  bcdFillZero(termAppVerNO);
	}

	public void setTermAppVerNO(String termAppVerNO) {
		if(termAppVerNO!= null && termAppVerNO.length()%2 !=0) {
			termAppVerNO = "0"+termAppVerNO;
		}
		this.termAppVerNO = termAppVerNO;
	}

	public String getTxnSequenceCounter() {
		return  bcdFillZero(txnSequenceCounter);
	}

	public void setTxnSequenceCounter(String txnSequenceCounter) {
		if(txnSequenceCounter!= null && txnSequenceCounter.length()%2 !=0) {
			txnSequenceCounter = "0"+txnSequenceCounter;
		}
		this.txnSequenceCounter = txnSequenceCounter;
	}

	public String getIssuerAuthData() {
		return  bcdFillZero(issuerAuthData);
	}

	public void setIssuerAuthData(String issuerAuthData) {
		if(issuerAuthData!= null && issuerAuthData.length()%2 !=0) {
			issuerAuthData = "0"+issuerAuthData;
		}
		this.issuerAuthData = issuerAuthData;
	}

	public String getIssuerScriptTem1() {
		return  bcdFillZero(issuerScriptTem1);
	}

	public void setIssuerScriptTem1(String issuerScriptTem1) {
		if(issuerScriptTem1!= null && issuerScriptTem1.length()%2 !=0) {
			issuerScriptTem1 = "0"+issuerScriptTem1;
		}
		this.issuerScriptTem1 = issuerScriptTem1;
	}

	public String getIssuerScriptTem2() {
		return  bcdFillZero(issuerScriptTem2);
	}

	public void setIssuerScriptTem2(String issuerScriptTem2) {
		if(issuerScriptTem2!= null && issuerScriptTem2.length()%2 !=0) {
			issuerScriptTem2 = "0"+issuerScriptTem2;
		}
		this.issuerScriptTem2 = issuerScriptTem2;
	}

	public String getIssuerScriptResult() {
		return  bcdFillZero(issuerScriptResult);
	}

	public void setIssuerScriptResult(String issuerScriptResult) {
		if(issuerScriptResult!= null && issuerScriptResult.length()%2 !=0) {
			issuerScriptResult = "0"+issuerScriptResult;
		}
		this.issuerScriptResult = issuerScriptResult;
	}

	public String getCardProductID() {
		return  bcdFillZero(cardProductID);
	}

	public void setCardProductID(String cardProductID) {
		if(cardProductID!= null && cardProductID.length()%2 !=0) {
			cardProductID = "0"+cardProductID;
		}
		this.cardProductID = cardProductID;
	}


	public String getTrackAll() {
		return trackAll;
	}


	public void setTrackAll(String trackAll) {
		this.trackAll = trackAll;
	}
	
	

}
