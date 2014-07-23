package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class StrRangeValidator implements FieldValidator {

	public boolean validate(Object obj, Field field, Object value, Object[] args) {
		//字段为空不检验，由REQUIRED校验
		if (value == null||value.toString().trim().equals("")) {
			return true;
		}
		int strLen = value.toString().length();
		if (strLen >= Integer.valueOf(args[1].toString())
				&& strLen <= Integer.valueOf(args[0].toString())) {
			return true;
		} else {
			return false;
		}
	}

	public Class support() {
		return FieldValidate.STRRANGE.class;
	}

	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_STRRANGE;
	}

	public Object[] getValidateArgs(Annotation anno) {
		FieldValidate.STRRANGE strRange = (FieldValidate.STRRANGE) anno;
		return new Object[] { strRange.max(), strRange.min() };
	}

}
