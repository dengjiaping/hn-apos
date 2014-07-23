package me.andpay.timobileframework.util.tlv;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface TlvField {
	/**
	 * The tag Id.
	 * 
	 * @return
	 */
	public String value();
	
	public int index() default 0;
}

