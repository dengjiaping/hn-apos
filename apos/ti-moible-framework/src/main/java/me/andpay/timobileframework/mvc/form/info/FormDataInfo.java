package me.andpay.timobileframework.mvc.form.info;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FormValidator;

/**
 * 反射FormProcess 所需的所有反射信息
 * 
 * @author tinyliu
 * 
 */
@SuppressWarnings("rawtypes")
public class FormDataInfo {

	private FormInfo formInfo;

	private FieldErrorMsgTranslate translate;


	private Class formDataBeanClass;

	private FormValidator validator;

	private Map<String, FieldInfo> formFields = new HashMap<String, FieldInfo>();

	@SuppressWarnings("unchecked")
	public FormDataInfo(Class formBeanClass) {
		this.formInfo = (FormInfo) formBeanClass.getAnnotation(FormInfo.class);
		this.formDataBeanClass = formBeanClass;
		this.validator = (FormValidator) formBeanClass
				.getAnnotation(FormValidator.class);
		this.translate = (FieldErrorMsgTranslate) formBeanClass
				.getAnnotation(FieldErrorMsgTranslate.class);
		for(Field field : formBeanClass.getDeclaredFields()) {
			formFields.put(field.getName(), new FieldInfo(field));
		}
	}

	public FieldInfo getFieldInfo(String paramName) {
		return formFields.get(paramName);
	}

	public Class getFormDataBeanClass() {
		return formDataBeanClass;
	}

	public FormInfo getFormInfo() {
		return formInfo;
	}

	public FieldErrorMsgTranslate getTranslate() {
		return translate;
	}

	public Collection<FieldInfo> getFormFields() {
		return formFields.values();
	}
	

	public FormValidator getValidator() {
		return validator;
	}

}
