package me.andpay.timobileframework.test.mvc.form.validation.translate;

import me.andpay.timobileframework.mvc.form.validation.translate.ErrorMsgTranslate;

public class ErrorMsgTranslaterTest implements ErrorMsgTranslate {

	public String translateError(String errorCode, String fieldName,
			Object[] params, Object formBean) {
		// TODO Auto-generated method stub
		return "testError";
	}

}
