package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class PhoneNumberValidator extends MaskValidator {

	private Pattern pattern = Pattern.compile("^((1[0-9][0-9]))\\d{8}$");

	@Override
	public Class support() {
		return FieldValidate.PHONENUMBER.class;
	}

	@Override
	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_PHONENUMBER;
	}

	@Override
	public Pattern getPattern(Object[] patternStr) {
		return pattern;
	}

	public Object[] getValidateArgs(Annotation anno) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
