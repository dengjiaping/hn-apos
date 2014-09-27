package me.andpay.apos.tam.flow.model;

import java.io.Serializable;

/**
 * 签名上下文
 * 
 * @author cpz
 *
 */
public class SignContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2873723288419286346L;

	/**
	 * 显示金额
	 */
	private String showAmt;
	/**
	 * 金额显示的颜色
	 */
	private int amtTextColor;

	/**
	 * 比划数
	 */
	private int gesturesCount;

	/**
	 * 比划长度
	 */
	private int gesturesLength;

	/**
	 * 终端流水号
	 */
	private String termTraceNo;
	/**
	 * 终端时间
	 */
	private String termTxnTime;
	/**
	 * 分配上传签名编号（用于补传签名）
	 */
	private String idUnderType;
	/**
	 * 签名路径
	 */
	private String signFileURL;

	private boolean showBackBtn;

	public String getShowAmt() {
		return showAmt;
	}

	public void setShowAmt(String showAmt) {
		this.showAmt = showAmt;
	}

	public int getAmtTextColor() {
		return amtTextColor;
	}

	public void setAmtTextColor(int amtTextColor) {
		this.amtTextColor = amtTextColor;
	}

	public int getGesturesCount() {
		return gesturesCount;
	}

	public void setGesturesCount(int gesturesCount) {
		this.gesturesCount = gesturesCount;
	}

	public int getGesturesLength() {
		return gesturesLength;
	}

	public void setGesturesLength(int gesturesLength) {
		this.gesturesLength = gesturesLength;
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

	public String getSignFileURL() {
		return signFileURL;
	}

	public void setSignFileURL(String signFileURL) {
		this.signFileURL = signFileURL;
	}

	public boolean isShowBackBtn() {
		return showBackBtn;
	}

	public void setShowBackBtn(boolean showBackBtn) {
		this.showBackBtn = showBackBtn;
	}

	public String getIdUnderType() {
		return idUnderType;
	}

	public void setIdUnderType(String idUnderType) {
		this.idUnderType = idUnderType;
	}

}
