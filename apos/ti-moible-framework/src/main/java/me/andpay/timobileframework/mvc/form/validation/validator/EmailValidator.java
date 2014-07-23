package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class EmailValidator extends MaskValidator {

	private Pattern pattern = Pattern
			.compile("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*");

	@Override
	public Class support() {
		return FieldValidate.EMAIL.class;
	}

	@Override
	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_EMAIL;
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
