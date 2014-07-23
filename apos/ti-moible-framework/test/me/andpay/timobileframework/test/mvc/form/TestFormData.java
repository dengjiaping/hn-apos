package me.andpay.timobileframework.test.mvc.form;

import java.lang.annotation.Annotation;

import me.andpay.timobileframework.mvc.form.annotation.FormInfo;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldErrorMsgTranslate.TRANSTYPE;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.COMMMONCHAR;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.EQUALSFIELD;
import me.andpay.timobileframework.mvc.form.validation.anno.FieldValidate.REQUIRED;

@FormInfo(action = "test", domain = "test", formName = "TestFormData")
@FieldErrorMsgTranslate(transType=TRANSTYPE.RESOURCE, resouceInfo="properties/testErrorMsg.properties")
public class TestFormData {
	
	@FieldValidate.CHINESECHAR
	private String chineseStr = "我是中国人";
	
	@FieldValidate.INTEGER
	private int age = 23;
	
	@FieldValidate.INTEGERRANGE(max = 100, min = 1)
	private int maxAge = 50;
	
	@FieldValidate.DOUBLE
	private double price = 15.22;
	
	@FieldValidate.DOUBLERANGE(max = 20, min = 1)
	private double maxPrice = 15.22;
	
	@FieldValidate.REQUIRED
	private Object required = new Object();
	
	private String equalsValue = "equals";
	
	@EQUALSFIELD(paramName = "equalsValue")
	@REQUIRED
	@COMMMONCHAR
	private String equals = "equals";
	
	@FieldValidate.EMAIL
	private String email = "tiny.liu@99bill.com";
	
	@FieldValidate.PHONENUMBER
	private String phone = "13766666666";
	
	@FieldValidate.MASK(pattern = "^(13[4,5,6,7,8,9]|15[0,8,9,1,7]|188|187)\\d{8}$")
	private String mask = "13766666666";
	
	@FieldValidate.COMMMONCHAR
	private String commonChar = "123_abc";

	public String getChineseStr() {
		return chineseStr;
	}

	public void setChineseStr(String chineseStr) {
		this.chineseStr = chineseStr;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public Object getRequired() {
		return required;
	}

	public void setRequired(Object required) {
		this.required = required;
	}

	public String getEqualsValue() {
		return equalsValue;
	}

	public void setEqualsValue(String equalsValue) {
		this.equalsValue = equalsValue;
	}

	public String getEquals() {
		return equals;
	}

	public void setEquals(String equals) {
		this.equals = equals;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getCommonChar() {
		return commonChar;
	}

	public void setCommonChar(String commonChar) {
		this.commonChar = commonChar;
	}	
	
	public static void main(String [] args) throws SecurityException, NoSuchFieldException {
		java.lang.reflect.Field field = TestFormData.class.getDeclaredField("equals");
		Annotation anno = field.getAnnotation(EQUALSFIELD.class);
		//Annotation [] annos = anno.annotationType().getAnnotations();
		System.out.println(anno.annotationType().isAnnotationPresent(FieldValidate.class));
	}
}
