package me.andpay.timobileframework.mvc.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.andpay.timobileframework.mvc.AbstractEventController;

@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.FIELD})
public @interface EventDelegate {
	
	public static enum DelegateType {
		method, eventController;
	}
	DelegateType type() default DelegateType.eventController;
	String delegate() default "";
	Class<?> delegateClass();
	String toMethod() default "";
	Class<? extends AbstractEventController> toEventController() default AbstractEventController.class;
	boolean isNeedFormBean() default false;
	
	/**
	 * 是否不拦截快速点击
	 */
	boolean isPassFastClick() default false;
}
