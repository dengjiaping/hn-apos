package me.andpay.apos.merchantservice.data;

import me.andpay.apos.lft.data.BaseData;

/*结算明细*/
public class SelementOrder implements BaseData {
	/* 标题 */
	private String title;
	/* 交易类型 */
	private String tradingCount;
	/* 时间 */
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

	

	public String getTradingCount() {
		return tradingCount;
	}

	public void setTradingCount(String tradingCount) {
		this.tradingCount = tradingCount;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

}
