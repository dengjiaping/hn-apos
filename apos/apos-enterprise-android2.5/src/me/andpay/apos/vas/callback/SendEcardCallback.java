package me.andpay.apos.vas.callback;

public interface SendEcardCallback {

	/**
	 * 网络异常
	 */
	public void netWorkError();

	/**
	 * 开卡成功
	 */
	public void sendSuccess();

	/**
	 * 开卡失败
	 * 
	 * @param errorMsg
	 */
	public void sendFaild(String errorMsg);
}
