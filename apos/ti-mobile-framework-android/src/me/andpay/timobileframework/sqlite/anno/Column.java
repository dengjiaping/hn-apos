package me.andpay.timobileframework.sqlite.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.andpay.timobileframework.sqlite.convert.DataConverter;


@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.FIELD})
public @interface Column {
	/**
	 * 字段名
	 * @return
	 */
	String name() default "";
	
	/**
	 * 数据转化器
	 * @return
	 */
	Class<? extends DataConverter> dataConverter() default  DataConverter.class;
}
