package me.andpay.timobileframework.mvc.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.TYPE}) 
public @interface FormInfo {
	String domain();
	
	String action();
	
	String formName();
	
	boolean isHasDynamicField() default false;
}
