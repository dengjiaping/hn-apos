package me.andpay.apos.tam.callback;

import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.TxnActionResponse;

public interface TxnCallback{

	/**
	 * 提示交易成功
	 * 
	 * @param response
	 */
	public void txnSuccess(TxnActionResponse actionResponse);

	/**
	 * 显示失败
	 * 
	 * @param msg
	 */
	public void showFaild(TxnActionResponse actionResponse);

	/**
	 * 交易超时
	 */
	public void onTimeout(TxnActionResponse actionResponse);

	/**
	 * 密码错误重输
	 */
	public void showInputPassword(TxnActionResponse actionResponse);

	/**
	 * 网络异常
	 */
	public void networkError(TxnActionResponse actionResponse);

	/**
	 * 初始化callback
	 * 
	 * @param txnControl
	 */
	public void initCallBack(TxnControl txnControl);
}
