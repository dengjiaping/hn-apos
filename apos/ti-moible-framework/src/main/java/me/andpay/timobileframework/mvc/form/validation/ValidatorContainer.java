package me.andpay.timobileframework.mvc.form.validation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.CHINESECHAR;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.COMMMONCHAR;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.DOUBLE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.DOUBLERANGE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.EMAIL;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.EQUALSFIELD;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.INTEGER;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.INTEGERRANGE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.MASK;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.PHONENUMBER;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.REQUIRED;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.STRRANGE;
import me.andpay.timobileframework.mvc.form.validation.validator.ChineseCharValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.CommonCharValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.DoubleRangeValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.DoubleValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.EmailValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.EqualsFieldValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.IntegerRangeValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.IntegerValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.MaskValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.PhoneNumberValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.RequiredValidator;
import me.andpay.timobileframework.mvc.form.validation.validator.StrRangeValidator;

class ValidatorContainer {
	
	private static Map<Class, FieldValidator> validators = new ConcurrentHashMap<Class, FieldValidator>();
	
	private static Map<Class, FormDataValidator> formDataValidator = new ConcurrentHashMap<Class, FormDataValidator>();
	/**
	 * 初始化默认组件
	 */
	static {
		validators.put(CHINESECHAR.class, new ChineseCharValidator());
		validators.put(COMMMONCHAR.class, new CommonCharValidator());
		validators.put(DOUBLERANGE.class, new DoubleRangeValidator());
		validators.put(DOUBLE.class, new DoubleValidator());
		validators.put(EMAIL.class, new EmailValidator());
		validators.put(EQUALSFIELD.class, new EqualsFieldValidator());
		validators.put(INTEGERRANGE.class, new IntegerRangeValidator());
		validators.put(INTEGER.class, new IntegerValidator());
		validators.put(MASK.class, new MaskValidator());
		validators.put(PHONENUMBER.class, new PhoneNumberValidator());
		validators.put(REQUIRED.class, new RequiredValidator());
		validators.put(STRRANGE.class, new StrRangeValidator());
	}
	
	public static void registeFieldValidator(FieldValidator validator) {
		validators.put(validator.support(), validator);
	}
	
	public static FieldValidator getFieldValidator(Class annoClass) {
		return validators.get(annoClass);
	}
	
	public static void registeFormDataValidator(FormDataValidator validator) {
		formDataValidator.put(validator.getClass(), validator);
	}
	
	public static FormDataValidator getFormDataValidator(Class validator) {
		return formDataValidator.get(validator);
	}
}
