package me.andpay.timobileframework.mvc.form.exception;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;

@SuppressWarnings("serial")
public class FormException extends Exception {

	private String errorCode = null;
	
	private String errorMsg = null;
	
	public FormException(String errorCode) {
		super(FormProcessErrorCode.getErrorMsg(errorCode));
		this.errorCode = errorCode;
	}
	
	public FormException(String errorCode, Exception ex) {
		super(FormProcessErrorCode.getErrorMsg(errorCode), ex);
		this.errorCode = errorCode;
	}
	
	public FormException(String errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
		this.errorMsg = msg;
	}
	
	public FormException(String errorCode, String msg, Exception ex) {
		super(msg, ex);
		this.errorCode = errorCode;
		this.errorMsg = msg;
		
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	
	
}
