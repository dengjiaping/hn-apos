package me.andpay.apos.cdriver;

public class InitIcCardResult {
	
	
	/**
	 * 错误消息
	 */
	private String errorMsg;
	/**
	 * 是否成功
	 */
	private boolean success;
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	
	
}
