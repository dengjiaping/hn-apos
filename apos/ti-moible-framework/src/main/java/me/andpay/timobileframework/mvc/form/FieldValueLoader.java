package me.andpay.timobileframework.mvc.form;

import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.exception.FormException;

@SuppressWarnings("rawtypes")
public interface FieldValueLoader {
	/**
	 * Resolver Field Value 
	 * @param id
	 * @param container
	 * @param field
	 * @param formBean
	 * @return
	 */
	public Object loadValue(ValueContainer container, Field field, FormBean formBean, Integer id) throws FormException;
	
}
 