package me.andpay.timobileframework.mvc.form.validation.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FormProcessErrorCode;
import me.andpay.timobileframework.mvc.form.validation.FieldValidator;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;

public class DoubleRangeValidator implements FieldValidator{

	public boolean validate(Object obj, Field field, Object value, Object[] args) {
		if(value == null) {
			return true;
		}
		Double doubleValue = null;
		try{
			doubleValue = Double.parseDouble(value.toString());
		} catch(Exception ex) {
			return false;
		}
		
		if(doubleValue >= Double.valueOf(args[1].toString()) && doubleValue <= Double.valueOf(args[0].toString())) {
			return true;
		} else {
			return false;
		}
	}

	public Class support() {
		return FieldValidate.DOUBLERANGE.class;
	}

	public String getErrorCode() {
		return FormProcessErrorCode.ERROR_VALIDATE_DOUBLERANGE;
	}
	
	public Object[] getValidateArgs(Annotation anno) {
		FieldValidate.DOUBLERANGE doubleAnno = (FieldValidate.DOUBLERANGE)anno;
		return new Object[] {doubleAnno.max(), doubleAnno.min()};
	}
}