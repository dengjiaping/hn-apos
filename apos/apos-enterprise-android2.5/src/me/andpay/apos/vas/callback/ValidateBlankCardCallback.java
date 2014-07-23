package me.andpay.apos.vas.callback;

public interface ValidateBlankCardCallback {
	
	
	/**
	 * 网络异常
	 */
	public void netWorkError();
	
	/**
	 * 开卡成功
	 */
	public void validateSuccess();
	
	/**
	 * 开卡失败
	 * @param errorMsg
	 */
	public void validateFaild(String errorMsg);
}
