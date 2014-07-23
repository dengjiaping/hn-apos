package me.andpay.timobileframework.mvc.form.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 表单数据验证接口
 * @author tinyliu
 * 
 */
public interface FieldValidator {
	/*
	 * 验证表单数据
	 */
	public boolean validate(Object obj, Field field, Object value, Object[] validateArgs);
	/**
	 * 支持的枚举类型
	 * @return
	 */
	public Class support();
	/**
	 * 获取默认参数
	 * @return
	 */
	public String getErrorCode();
	/**
	 * 获取默认枚举参数
	 * @return
	 */
	public Object[] getValidateArgs(Annotation anno);
}
