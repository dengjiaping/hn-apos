package me.andpay.timobileframework.test.mvc.form;

import java.lang.reflect.Field;

import me.andpay.timobileframework.mvc.form.FieldValueLoader;
import me.andpay.timobileframework.mvc.form.FormBean;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import me.andpay.timobileframework.mvc.form.exception.FormException;

public class MockFieldValueLoader implements FieldValueLoader{

	public Object loadValue(ValueContainer container, Field field,
			FormBean formBean, Integer id) throws FormException {
		
		if(field.getName().equalsIgnoreCase("chineseStr")) {
			return "测试数据";
		}
		if(field.getName().equalsIgnoreCase("age")) {
			return new Integer(23);
		}
		if(field.getName().equalsIgnoreCase("maxAge")) {
			return new Integer(23);
		}
		if(field.getName().equalsIgnoreCase("price")) {
			return "asb";
		}
		if(field.getName().equalsIgnoreCase("maxPrice")) {
			return new Double("20.123");
		}
		if(field.getName().equalsIgnoreCase("required")) {
			return "测试required数据";
		}
		if(field.getName().equalsIgnoreCase("equalsValue")) {
			return "测试equals数据";
		}
		if(field.getName().equalsIgnoreCase("equals")) {
			return "测试equals数据";
		}
		if(field.getName().equalsIgnoreCase("email")) {
			return "测试email数据";
		}
		if(field.getName().equalsIgnoreCase("phone")) {
			return "测试phone数据";
		}
		if(field.getName().equalsIgnoreCase("commonChar")) {
			return "测试通用数据";
		}
		return null;
	}

}
