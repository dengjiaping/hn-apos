package me.andpay.timobileframework.mvc.form.validation;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.andpay.timobileframework.mvc.form.info.FieldInfo;
import me.andpay.timobileframework.mvc.form.info.ValidateInfo;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.REQUIRED;

public class FieldValidateInfo {

	private Map<Class, Object[]> validates = new HashMap<Class, Object[]>();

	private String fieldName = null;
	
	private boolean hasRequiredValidate = false;

	FieldValidateInfo(FieldInfo field, List<ValidateInfo> dynamicValidates) {
		fieldName = field.getName();
		for (Annotation anno : field.getFieldValidates()) {
			validates.put(anno.annotationType(), ValidatorContainer
					.getFieldValidator(anno.annotationType()).getValidateArgs(anno));
		}
		if (dynamicValidates != null) {
			for (ValidateInfo info : dynamicValidates) {
				validates.put(info.getValidateAnno(), info.getArgs());
			}
		}
		if(validates.containsKey(REQUIRED.class)) {
			hasRequiredValidate = true;
			validates.remove(REQUIRED.class);
		}

	}

	public Map<Class, Object[]> getValidates() {
		return validates;
	}

	public String getFieldName() {
		return fieldName;
	}

	public boolean isHasRequiredValidate() {
		return hasRequiredValidate;
	}

	
}
