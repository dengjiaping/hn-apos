package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.util.regex.Pattern;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class ChineseCharValidator extends MaskValidator {

	private Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]*$");

	public Class support() {
		return FieldValidate.CHINESECHAR.class;
	}

	@Override
	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_CHINESECHAR;
	}

	@Override
	public Pattern getPattern(Object[]  patternStr) {
		return pattern;
	}
	

	@Override
	public Object[] getValidateArgs(Annotation anno) {
		return null;
	}
}
