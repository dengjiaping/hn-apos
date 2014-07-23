package me.andpay.timobileframework.mvc.form.validation.translate;

import me.andpay.timobileframework.mvc.form.FormDataInfoResolver;
import me.andpay.timobileframework.mvc.form.exception.FormException;
import me.andpay.timobileframework.mvc.form.info.FormDataInfo;
import me.andpay.timobileframework.mvc.form.validation.ValidateErrorInfo;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;

public class FormErroMsgTranslater{

	public void translateErrors(Object obj, ValidateErrorInfo[] errors) throws  FormException {
		// 判断是否定义了转换器
		FormDataInfo reflectInfo = FormDataInfoResolver
				.resolver(obj.getClass());
		if (reflectInfo.getTranslate() == null) {
			return;
		}
		FieldErrorMsgTranslate tranInfo = reflectInfo.getTranslate();
		ErrorMsgTranslate translate = ErrorMsgTranslateSelector.selectTranslate(tranInfo);
		for (ValidateErrorInfo error : errors) {
			String errorMsg = translate.translateError(error.getErrorCode(),
					error.getFieldName(), error.getErrorDescriptionParam(), obj);
			error.setErrorDescription(errorMsg);
		}
	}

}
