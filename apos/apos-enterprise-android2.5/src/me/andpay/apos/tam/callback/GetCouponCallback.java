package me.andpay.apos.tam.callback;

import me.andpay.ac.term.api.pas.Coupon;

public interface GetCouponCallback {
	
	/**
	 * 兑换成功
	 * @param coupon
	 */
	public void getCouponSuccess(Coupon coupon);

	/**
	 * 兑换失败
	 * @param errorMsg
	 */
	public void getCouponFaild(String errorMsg);
	
	
	/**
	 * 网络异常
	 * @param errorMsg
	 */
	public void getCouponNetworkError(String errorMsg);
}
