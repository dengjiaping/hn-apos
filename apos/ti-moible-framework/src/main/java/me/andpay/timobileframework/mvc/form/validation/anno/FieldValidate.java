package me.andpay.timobileframework.mvc.form.validation.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * field validator枚举
 * 
 * 自定义validator需要： 1.自定义validate Annotion，并且在TYPE上增加@FieldValidate
 * 2.实现FieldValidator接口 3.注册到Module中
 * 
 * @author tinyliu
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface FieldValidate {

	// required 必须，代表这个域不能为空 无参数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface REQUIRED {

	}

	// integer: 没有或者必须为整数 无参数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface INTEGER {
	}

	// double: 允许为空或者必须为double数 无参数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface DOUBLE {
	}

	// commonChar 普通英文字符：字母数字和下划线 无参数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface COMMMONCHAR {
	}

	// chineseChar: 中文字符 无参数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface CHINESECHAR {
	}

	// email: 必须为Email格式 无参数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface EMAIL {
	}

	// phoneNumber: 必须为电话号码格式 无参数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface PHONENUMBER {
	}

	// integerRange: 整数范围必须在参数0和参数1之间。 param0和param1必须能被转化成整数
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface INTEGERRANGE {
		int min();

		int max();
	}

	// doubleRange: double数的范围必须在参数0和参数1之间 参数0和参数1必须能被转化成Float。
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface DOUBLERANGE {
		double min();

		double max();
	}

	// equalsField: 必须与某一个域的值相等 param0：同一个表单中域的名称。例如用来校验密码
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface EQUALSFIELD {
		String paramName();
	}

	// mask: 根据提供的正则表达式匹配
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface MASK {
		String pattern();
	}

	// mask: 根据提供的正则表达式匹配
	@Retention(RetentionPolicy.RUNTIME)
	@Target({ ElementType.FIELD })
	@FieldValidate
	public static @interface STRRANGE {
		int max();
		int min();
	}
}
