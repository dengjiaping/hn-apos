package me.andpay.apos.merchantservice.data;

import me.andpay.apos.lft.data.BaseData;

/**
 * 结算详细订单
 * 
 * @author Administrator
 *
 */
public class SettlementDetailOrder implements BaseData {
	/* 标题 */
	private String title;
	
	private String time;

	/* 交易金额 */
	private String tradingAccounts;
	/* 商户费用 */
	private String merchantsCost;
	/* 结算金额 */
	private String settlementAccount;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTradingAccounts() {
		return tradingAccounts;
	}

	public void setTradingAccounts(String tradingAccounts) {
		this.tradingAccounts = tradingAccounts;
	}

	public String getMerchantsCost() {
		return merchantsCost;
	}

	public void setMerchantsCost(String merchantsCost) {
		this.merchantsCost = merchantsCost;
	}

	public String getSettlementAccount() {
		return settlementAccount;
	}

	public void setSettlementAccount(String settlementAccount) {
		this.settlementAccount = settlementAccount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
