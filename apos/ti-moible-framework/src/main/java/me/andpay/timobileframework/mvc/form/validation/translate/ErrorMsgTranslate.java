package me.andpay.timobileframework.mvc.form.validation.translate;


/*
 * 错误信息翻译接口
 */
public interface ErrorMsgTranslate {
	/**
	 * valida
	 * 
	 * @param errorCode
	 * @param fieldName
	 * @param params
	 * @return
	 */
	public String translateError(String errorCode, String fieldName, Object [] params, Object formBean);
	
}
