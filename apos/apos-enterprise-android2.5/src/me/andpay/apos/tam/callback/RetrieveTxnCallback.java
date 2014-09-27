package me.andpay.apos.tam.callback;

public interface RetrieveTxnCallback {

	/**
	 * 交易成功
	 */
	public void txnSuccess();

	/**
	 * 交易失败
	 * 
	 * @param errorMsg
	 */
	public void txnFaild(String errorMsg);

	/**
	 * 网络异常
	 */
	public void netWorkerror();

}
