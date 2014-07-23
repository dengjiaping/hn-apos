package me.andpay.timobileframework.mvc.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.TYPE}) 
public @interface DynamicField {
	public static Integer defaultId = -1;
}
