package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.EQUALSFIELD;

public class EqualsFieldValidator implements FieldValidator {

	public boolean validate(Object obj, Field field, Object value,
			Object[] args) {
		Object equalsValue = null;
		try {
			Field equalsField = obj.getClass().getDeclaredField(args[0].toString());
			equalsField.setAccessible(true);
			equalsValue = equalsField.get(obj);
		} catch (Exception e) {
			return false;
		}

		if (value == null && equalsValue == null) {
			return true;
		}
		return value == null ? equalsValue.equals(value) : value
				.equals(equalsValue);
	}

	public Class support() {
		return EQUALSFIELD.class;
	}

	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_EQUALSFIELD;
	}

	public Object[] getValidateArgs(Annotation anno) {
		// TODO Auto-generated method stub
		return new Object[]{((EQUALSFIELD)anno).paramName()};
	}


}
