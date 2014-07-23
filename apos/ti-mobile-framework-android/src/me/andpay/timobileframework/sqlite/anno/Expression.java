package me.andpay.timobileframework.sqlite.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.andpay.timobileframework.sqlite.convert.DataConverter;


@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.FIELD})
public @interface Expression {
	
	
	/**
	 * 操作符
	 * @return
	 */
	String operater() default "";
	/**
	 * 数据库字段名
	 */
	String paraName() default "";
//	/**
//	 * 逻辑符号 or 或者 and 等
//	 */
//	String  logicSymbol() default "";
	
	/**
	 * sql 语句组装
	 * @return
	 */
	String  sqlformat() default "";

	
	/**
	 * 数据转化器
	 * @return
	 */
	Class<? extends DataConverter> dataConverter() default  DataConverter.class;

}
