package me.andpay.timobileframework.mvc.form.validation;


/**
 * 
 * @author tinyliu
 *
 */
public class ValidateErrorInfo {
	
	private String fieldName = null;
	
	private String errorCode = null;
	
	private String errorDescription = null;
	
	private Object [] errorDescriptionParam = null;
	/**
	 * 控件编号
	 */
	private int paramId;
	
	public Object[] getErrorDescriptionParam() {
		return errorDescriptionParam;
	}


	public void setErrorDescriptionParam(Object[] errorDescriptionParam) {
		this.errorDescriptionParam = errorDescriptionParam;
	}


	public ValidateErrorInfo(String fieldName, String errorCode) {
		this.fieldName = fieldName;
		this.errorCode = errorCode;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}


	public int getParamId() {
		return paramId;
	}


	public void setParamId(int paramId) {
		this.paramId = paramId;
	}
	
	
	
}
