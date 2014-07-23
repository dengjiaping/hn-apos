package me.andpay.apos.cdriver.model;


/**
 * 标准返回数据
 * @author cpz
 *
 */
public class AposResultData {
	
	
	/**
	 * 错误码
	 */
	private String errorCode;
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 返回的数据
	 */
	private String data;
	
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	
	
}
