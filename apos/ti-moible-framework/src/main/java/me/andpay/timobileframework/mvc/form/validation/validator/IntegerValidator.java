package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

/**
 * 整数验证器
 * @author tinyliu
 *
 */
public class IntegerValidator implements FieldValidator {

	public boolean validate(Object obj, Field field, Object value, Object[] args) {
		if(value == null) {
			return true;
		}
		try{
			Integer.parseInt(value.toString());
		} catch(Exception ex) {
			return false;
		}
		return true;
		
	}

	public Class support() {
		return FieldValidate.INTEGER.class;
	}
	
	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_INTEGER;
	}

	public Object[] getValidateArgs(Annotation anno) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
