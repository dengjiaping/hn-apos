package me.andpay.apos.tam.callback;

import me.andpay.apos.tam.form.TxnForm;

/**
 * 云Pos操作类
 * 
 * @author tinyliu
 * 
 */
public interface CloudPosCallback {
	/**
	 * 操作成功
	 * 
	 * @param form
	 */
	public void pushOrderSucc(String cloudOrderId);

	/**
	 * 网络异常
	 * 
	 * @param form
	 */
	public void pushOrderNetworkError(String errorMsg);
}
