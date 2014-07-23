package me.andpay.apos.tqrm.form;


public class QueryCouponCondForm {

	private Long redeemId;

	private Long maxRedeemId;

	private Long minRedeemId;

	private String orders;

	public Long getRedeemId() {
		return redeemId;
	}

	public void setRedeemId(Long redeemId) {
		this.redeemId = redeemId;
	}

	public Long getMaxRedeemId() {
		return maxRedeemId;
	}

	public void setMaxRedeemId(Long maxRedeemId) {
		this.maxRedeemId = maxRedeemId;
	}

	public Long getMinRedeemId() {
		return minRedeemId;
	}

	public void setMinRedeemId(Long minRedeemId) {
		this.minRedeemId = minRedeemId;
	}

	public String getOrders() {
		return orders;
	}

	public void setOrders(String orders) {
		this.orders = orders;
	}

}
