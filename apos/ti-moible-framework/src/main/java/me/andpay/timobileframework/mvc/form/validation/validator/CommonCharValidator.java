package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class CommonCharValidator extends MaskValidator {

	private Pattern pattern = Pattern.compile("^[0-9a-zA-Z_]+$");
	
	@Override
	public Class support() {
		return FieldValidate.COMMMONCHAR.class;
	}

	@Override
	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_COMMONCHAR;
	}

	@Override
	public Pattern getPattern(Object[] patternStr) {
		return pattern;
	}

	@Override
	public Object[] getValidateArgs(Annotation anno) {
		return null;
	}
}
