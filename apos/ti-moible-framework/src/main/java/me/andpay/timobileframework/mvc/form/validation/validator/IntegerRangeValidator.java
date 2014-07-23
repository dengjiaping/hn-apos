package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class IntegerRangeValidator implements FieldValidator{

	public boolean validate(Object obj, Field field, Object value, Object[] args) {
		if(value == null) {
			return true;
		}
		Integer intValue = null;
		try{
			intValue = Integer.parseInt(value.toString());
		} catch(Exception ex) {
			return false;
		}
		if(intValue >= Integer.valueOf(args[1].toString()) && intValue <= Integer.valueOf(args[0].toString())) {
			return true;
		} else {
			return false;
		}
	}

	public Class support() {
		return FieldValidate.INTEGERRANGE.class;
	}

	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_INTEGERRANGE;
	}

	public Object[] getValidateArgs(Annotation anno) {
		FieldValidate.INTEGERRANGE intAnno = (FieldValidate.INTEGERRANGE)anno;
		return new Object[] {intAnno.max(), intAnno.min()};
	}
}
