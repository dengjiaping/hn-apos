package me.andpay.timobileframework.mvc.form.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
 * @author tinyliu
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.TYPE}) 
public @interface FormBind {
	Class formBean();
}
