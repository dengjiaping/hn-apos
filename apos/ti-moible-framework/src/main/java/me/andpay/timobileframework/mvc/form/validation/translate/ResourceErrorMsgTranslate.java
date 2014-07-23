package me.andpay.timobileframework.mvc.form.validation.translate;

import java.io.IOException;
import java.util.Properties;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.exception.FormException;


/**
 * errorMsg转换规则：
 * 
 * 从指定的Properties文件中查找，查找优先级别如下：
 * 1.key = formBeanClassName + "_" + fieldName + "_" ＋ errorCode ;
 * 2.key = "errorCode" ;
 * @author tinyliu
 *
 */

public class ResourceErrorMsgTranslate implements ErrorMsgTranslate{
	
	private Properties prop = null;

	
	public ResourceErrorMsgTranslate(String resouresInfo) throws FormException {
		prop = new Properties();
		try {
			prop.load( Thread.currentThread().getContextClassLoader().getResourceAsStream(resouresInfo));
		} catch (IOException e) {
			throw new FormException(FormProcessErrorCode.ERROR_TRANSLATE_INITPROP, e);
		}
	}
	
	public String translateError(String errorCode, String fieldName,
			Object[] params, Object formBean) {
		Object obj = prop.get(getExactKey(errorCode, fieldName, formBean));
		if(obj == null) {
			obj = prop.get(errorCode);
		}
		if(obj == null) {
			return null;
		}
		return String.format(obj.toString(), params);
	}
	
	private String getExactKey(String errorCode, String fieldName, Object formBean) {
		return formBean.getClass().getName() + "_" + fieldName + "_" + errorCode;
	}

}
