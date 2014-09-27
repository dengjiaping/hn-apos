package me.andpay.apos.vas.callback;

import me.andpay.ac.term.api.shop.PurchaseOrder;

public interface PurchaseOrderCallback {

	/**
	 * 网络异常
	 */
	public void networkError();

	// /**
	// * 订单授权失败
	// * @param errorMsg
	// */
	// public void placeOrderFailed(String errorMsg);

	/**
	 * 订单授权成功
	 */
	public void placeOrderSuccess(PurchaseOrder purchaseOrder);
}
