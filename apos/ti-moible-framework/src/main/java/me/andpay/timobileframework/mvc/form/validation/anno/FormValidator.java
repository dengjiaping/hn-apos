package me.andpay.timobileframework.mvc.form.validation.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.andpay.timobileframework.mvc.form.validation.FormDataValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface FormValidator {
	Class<? extends FormDataValidator> validator();
}
