package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class MaskValidator implements FieldValidator {

	
	public boolean validate(Object obj, Field field, Object value, Object[] args) {
		if (value == null || StringUtil.isEmpty(value.toString())) {
			return true;
		}
		Matcher matcher = getPattern(args).matcher(value.toString());
		return matcher.find();
	}

	public Class support() {
		return FieldValidate.MASK.class;
	}

	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_MASK;
	}
	
	public Pattern getPattern(Object[] patternStr) {
		return Pattern.compile(patternStr[0].toString());
	}

	public Object[] getValidateArgs(Annotation anno) {
		// TODO Auto-generated method stub
		return new Object[]{((FieldValidate.MASK)anno).pattern()};
	}
}
