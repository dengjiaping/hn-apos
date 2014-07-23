package me.andpay.timobileframework.mvc.form.info;


public class ValidateInfo {
	
	private Class validateAnno;
	
	private Object[] args = null;
	
	public ValidateInfo(Class annoClass, Object[] args) {
		this.args = args;
		this.validateAnno = annoClass;
	}

	public Class getValidateAnno() {
		return validateAnno;
	}

	public Object[] getArgs() {
		return args;
	}
}
