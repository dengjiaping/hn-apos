package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class DoubleValidator implements FieldValidator{

	public boolean validate(Object obj,Field field, Object value, Object[] validateArgs) {
		if(value == null) {
			return true;
		}
		try{
			Double.parseDouble(value.toString());
		} catch(Exception ex) {
			return false;
		}
		return true;
		
	} 

	public Class support() {
		return FieldValidate.DOUBLE.class;
	}

	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_DOUBLE;
	}
	
	public Object[] getValidateArgs(Annotation anno) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
