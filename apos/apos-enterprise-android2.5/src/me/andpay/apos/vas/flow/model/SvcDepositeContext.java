package me.andpay.apos.vas.flow.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Bidi;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import android.app.Application;
import me.andpay.apos.R;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.ti.util.JacksonSerializer;

/**
 * 预付费卡充值上下文数据
 * 
 * @author tinyliu
 * 
 */
public class SvcDepositeContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ATTR_KEY_CARDNO = "cardNo";
	
	public static final String ATTR_KEY_CARDTYPE = "cardType";
	
	public static final String ATTR_KEY_CARDNAME = "cardName";

	/**
	 * 交易充值状态
	 */
	/*
	 * public final String SVC_DEPOSITE_STATUS_INIT = "0";
	 * 
	 * public final String SVC_DEPOSITE_STATUS_PAYING = "1";
	 * 
	 * public final String SVC_DEPOSITE_STATUS_PAYED = "2";
	 * 
	 * public final String SVC_DEPOSITE_STATUS_PLACEING = "3";
	 * 
	 * public final String SVC_DEPOSITE_STATUS_FINISHED = "4";
	 */

	/**
	 * 卡号
	 */
	private String cardNo;

	/**
	 * 2磁道信息
	 */
	private String track2;

	/**
	 * 预付费卡类型，对应ShopProductTypes
	 */
	private String cardType;

	/**
	 * 充值控制
	 */
	private SortedMap<BigDecimal, BigDecimal> depositCtrls;

	/**
	 * 充值金额
	 */
	private BigDecimal depositeAmount;

	/**
	 * 支付方式 对应 PaymentMethods
	 */
	private String paymentMethod;

	/**
	 * 对应消费订单ID
	 */
	private Long purchaseOrderId;

	/**
	 * 交易编号
	 */
	private String txnId;
	/**
	 * 充值状态
	 */
	// private String svcDepositeStatus;

	/**
	 * 卡余额
	 */
	private BigDecimal balance;
	/**
	 * 卡名称
	 */
	private String cardName;

	private String[] ctrlAmtDesc;

	private BigDecimal[] ctrlAmtArray;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public BigDecimal getDepositeAmount() {
		return depositeAmount;
	}

	public void setDepositeAmount(BigDecimal depositeAmount) {
		this.depositeAmount = depositeAmount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Long getPurchaseOrderId() {
		return purchaseOrderId;
	}

	public void setPurchaseOrderId(Long purchaseOrderId) {
		this.purchaseOrderId = purchaseOrderId;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public SortedMap<BigDecimal, BigDecimal> getDepositCtrls() {
		return depositCtrls;
	}

	public void setDepositCtrls(Application application,
			SortedMap<BigDecimal, BigDecimal> depositCtrls) {
		this.depositCtrls = depositCtrls;
		convertDepositCtrls(application);
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public String convertCardInfoToString() {
		Map<String, String> attr = new HashMap<String, String>();
		attr.put(ATTR_KEY_CARDNO, cardNo);
		attr.put(ATTR_KEY_CARDTYPE, cardType);
		attr.put(ATTR_KEY_CARDNAME, cardName);
		return new String(JacksonSerializer.newPrettySerializer().serialize(attr));
	}

	private void convertDepositCtrls(Application application) {
		if (this.depositCtrls == null || this.depositCtrls.isEmpty()) {
			return;
		}
		this.ctrlAmtDesc = new String[depositCtrls.size()];
		this.ctrlAmtArray = new BigDecimal[depositCtrls.size()];
		int i = 0;
		for (Map.Entry<BigDecimal, BigDecimal> entry : depositCtrls.entrySet()) {
			this.ctrlAmtDesc[i] = String.format(ResourceUtil.getString(application,
					R.string.vas_svc_deposite_price_format_str),
					entry.getKey().setScale(2).toPlainString(),
					entry.getValue().setScale(2).toPlainString());
			this.ctrlAmtArray[i] = entry.getKey();
			i++;
		}
	}

	public BigDecimal getSelectPrice(int index) {
		return this.ctrlAmtArray[index];
	}

	public String[] getCtrlAmtDesc() {
		return ctrlAmtDesc;
	}

	public BigDecimal[] getCtrlAmtArray() {
		return ctrlAmtArray;
	}

	public boolean isHasCtrlPrice() {
		if (this.depositCtrls == null || this.depositCtrls.isEmpty()) {
			return false;
		}
		return true;
	}

}
