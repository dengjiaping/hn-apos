package me.andpay.apos.vas.callback;

public interface OpenCardCallback {
	
	
	/**
	 * 网络异常
	 */
	public void netWorkError();
	
	/**
	 * 开卡成功
	 */
	public void openCardSuccess();
	
	/**
	 * 开卡失败
	 * @param errorMsg
	 */
	public void openCardFaild(String errorMsg);
}
