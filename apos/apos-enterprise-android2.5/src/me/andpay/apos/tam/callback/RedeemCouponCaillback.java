package me.andpay.apos.tam.callback;

public interface RedeemCouponCaillback {

	/**
	 * 读取优惠劵成功
	 */
	public void redeemSuccess(int count);

	/**
	 * 
	 * @param errorMsg
	 */
	public void redeemFail(String errorMsg);

	/**
	 * 网络异常
	 * 
	 * @param errorMsg
	 */
	public void redeemNetwork(String errorMsg);

}
