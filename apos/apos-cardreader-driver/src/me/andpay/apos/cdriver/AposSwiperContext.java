package me.andpay.apos.cdriver;

import java.math.BigDecimal;

import me.andpay.apos.cdriver.model.AposICCardDataInfo;

public class AposSwiperContext {

	// 键盘输入
	public static final int INPUT_KEY_BOARD = 0;
	// 读卡器输入
	public static final int INPUT_CARD_READER = 1;
	/**
	 * 卡信息
	 */
	/**
	 * 销售金额
	 */
	private BigDecimal salesAmt;

	/**
	 * 蓝牙编号
	 */
	private String bluetoothId;

	private String termTraceNo;

	private byte[] base64TermTraceNo;

	private String extType;

	private boolean needPin;

	private int pinErrorCount;

	private String cardNo;

	private byte[] bcdCardNo;

	private CardInfo cardInfo;

	private int cardreaderType;
	
	/**
	 * 是否是IC卡交易
	 */
	private boolean icCardTxn;

	/**
	 * ic信息域
	 */
	private AposICCardDataInfo aposICCardDataInfo;

	
	public boolean isIcCardTxn() {
		return icCardTxn;
	}

	public void setIcCardTxn(boolean icCardTxn) {
		this.icCardTxn = icCardTxn;
	}

	public CardInfo getCardInfo() {
		return cardInfo;
	}

	public void setCardInfo(CardInfo cardInfo) {
		this.cardInfo = cardInfo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public byte[] getBcdCardNo() {
		return bcdCardNo;
	}

	public void setBcdCardNo(byte[] bcdCardNo) {
		this.bcdCardNo = bcdCardNo;
	}

	public byte[] getBase64TermTraceNo() {
		return base64TermTraceNo;
	}

	public void setBase64TermTraceNo(byte[] base64TermTraceNo) {
		this.base64TermTraceNo = base64TermTraceNo;
	}

	public String getTermTraceNo() {
		return termTraceNo;
	}

	public void setTermTraceNo(String termTraceNo) {
		this.termTraceNo = termTraceNo;
	}

	public String getExtType() {
		return extType;
	}

	public void setExtType(String extType) {
		this.extType = extType;
	}

	public boolean isNeedPin() {
		return needPin;
	}

	public void setNeedPin(boolean needPin) {
		this.needPin = needPin;
	}

	public int getPinErrorCount() {
		return pinErrorCount;
	}

	public void setPinErrorCount(int pinErrorCount) {
		this.pinErrorCount = pinErrorCount;
	}

	public BigDecimal getSalesAmt() {
		return salesAmt;
	}

	public void setSalesAmt(BigDecimal salesAmt) {
		this.salesAmt = salesAmt;
	}

	public String getBluetoothId() {
		return bluetoothId;
	}

	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
	}

	public int getCardreaderType() {
		return cardreaderType;
	}

	public void setCardreaderType(int cardreaderType) {
		this.cardreaderType = cardreaderType;
	}

	public AposICCardDataInfo getAposICCardDataInfo() {
		return aposICCardDataInfo;
	}

	public void setAposICCardDataInfo(AposICCardDataInfo aposICCardDataInfo) {
		this.aposICCardDataInfo = aposICCardDataInfo;
	}
	

}
