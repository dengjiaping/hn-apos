package me.andpay.timobileframework.mvc.form.validation.translate;

import java.util.HashMap;
import java.util.Map;

import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;

/**
 * Translate选择器
 * 
 * @author tinyliu
 *
 */
public class ErrorMsgTranslateSelector {
	
	private static Map<String, ErrorMsgTranslate> resourceTrans = new HashMap<String, ErrorMsgTranslate>();
	
	private static Map<Class, ErrorMsgTranslate> implTrans = new HashMap<Class, ErrorMsgTranslate>();
	
	public static ErrorMsgTranslate selectTranslate(FieldErrorMsgTranslate transInfo) throws FormException {
		TRANSTYPE type = transInfo.transType();
		if(type == TRANSTYPE.RESOURCE) {
			return getResourceTrans(transInfo);
		} else {
			return implTrans.get(transInfo.Implements());
		}

	}
	
	public static void registeImplTranslate(ErrorMsgTranslate trans) {
		implTrans.put(trans.getClass(), trans);
	}

	private static ErrorMsgTranslate getResourceTrans(
			FieldErrorMsgTranslate transInfo) throws FormException {
		ErrorMsgTranslate trans = resourceTrans.get(transInfo.resouceInfo());
		if(trans == null) {
			trans = new ResourceErrorMsgTranslate(transInfo.resouceInfo());
		}
		resourceTrans.put(transInfo.resouceInfo(), trans);
		return trans;
	}
	
	
}
