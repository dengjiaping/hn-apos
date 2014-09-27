package me.andpay.apos.scm.flow.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class CardReaderSetContext implements Serializable {

	private static final long serialVersionUID = 1L;

	private int cardReaderType;

	private String ksn;

	/**
	 * 设备页面是否有返回按钮
	 */
	private boolean showBackButton = true;

	private boolean showSkipButton;

	/**
	 * 是否已经选择读卡器
	 */
	private boolean hasSelectCardreader;

	/**
	 * 是否提示过杜比
	 */
	private boolean dolbyHasPrompt;
	/**
	 * 是否在成功页面可继续向导
	 */
	private boolean showCardreaderGuide = true;
	/**
	 * 操作跟踪号
	 */
	private String opTraceNo;

	private Map<String, String> opLogData = new HashMap<String, String>();

	// 重试次数
	private int tryTimes;

	public int getCardReaderType() {
		return cardReaderType;
	}

	public void setCardReaderType(int cardReaderType) {
		this.cardReaderType = cardReaderType;
	}

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public boolean isShowBackButton() {
		return showBackButton;
	}

	public void setShowBackButton(boolean showBackButton) {
		this.showBackButton = showBackButton;
	}

	public boolean isShowSkipButton() {
		return showSkipButton;
	}

	public void setShowSkipButton(boolean showSkipButton) {
		this.showSkipButton = showSkipButton;
	}

	public boolean isHasSelectCardreader() {
		return hasSelectCardreader;
	}

	public void setHasSelectCardreader(boolean hasSelectCardreader) {
		this.hasSelectCardreader = hasSelectCardreader;
	}

	public boolean isDolbyHasPrompt() {
		return dolbyHasPrompt;
	}

	public void setDolbyHasPrompt(boolean dolbyHasPrompt) {
		this.dolbyHasPrompt = dolbyHasPrompt;
	}

	public boolean isShowCardreaderGuide() {
		return showCardreaderGuide;
	}

	public void setShowCardreaderGuide(boolean showCardreaderGuide) {
		this.showCardreaderGuide = showCardreaderGuide;
	}

	public String getOpTraceNo() {
		return opTraceNo;
	}

	public void setOpTraceNo(String opTraceNo) {
		this.opTraceNo = opTraceNo;
	}

	public Map<String, String> getOpLogData() {
		return opLogData;
	}

	public void setOpLogData(Map<String, String> opLogData) {
		this.opLogData = opLogData;
	}

	public int getTryTimes() {
		return tryTimes;
	}

	public void setTryTimes(int tryTimes) {
		this.tryTimes = tryTimes;
	}

}
