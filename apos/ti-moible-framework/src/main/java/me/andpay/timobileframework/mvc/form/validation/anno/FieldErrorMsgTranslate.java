package me.andpay.timobileframework.mvc.form.validation.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import me.andpay.timobileframework.mvc.TiMVCConst;
import me.andpay.timobileframework.mvc.form.validation.translate.ErrorMsgTranslate;

/**
 * 表单错误信息翻译标注
 * 对应的资源文件名称
 * 
 * 
 * @author tinyliu
 *
 */
@Retention(RetentionPolicy.RUNTIME)  
@Target({ElementType.TYPE}) 
public @interface FieldErrorMsgTranslate {
	
	public static enum TRANSTYPE {RESOURCE, IMPLEMENTS};
	
	TRANSTYPE transType() default TRANSTYPE.RESOURCE;
	
	String resouceInfo() default TiMVCConst.FORM_FIELD_ERROR_DEFAULT_PATH;
	
	Class<? extends ErrorMsgTranslate> Implements() default ErrorMsgTranslate.class;
}
