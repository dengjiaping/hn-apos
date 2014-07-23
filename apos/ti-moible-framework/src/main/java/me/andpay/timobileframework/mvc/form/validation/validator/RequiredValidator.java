package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

/**
 * 验证参数是否是必须
 * @author tinyliu
 *
 */
public class RequiredValidator implements FieldValidator{

	public boolean validate(Object obj, Field filed, Object value, Object[] args) {
		if(value == null||value.toString().trim().equals("")) {
			return false;
		}
		return true;
	}

	public Class support() {
		return FieldValidate.REQUIRED.class;
	}

	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_REQUIRED;
	}


	public Object[] getValidateArgs(Annotation anno) {
		// TODO Auto-generated method stub
		return null;
	}
}
