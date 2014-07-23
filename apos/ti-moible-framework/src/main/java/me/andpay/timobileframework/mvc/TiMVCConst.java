package me.andpay.timobileframework.mvc;

public interface TiMVCConst {
	/**
	 * Annotation解析表单服务
	 */
	public static String FORM_RESOLVERVALUE_ANNO_NAMES = "FormDefinitionByAnnoResolver";
	/**
	 * Xml解析表单服务
	 */
	public static String FORM_RESOLVERVALUE_XML_NAMES = "FormDefinitionByXmlResolver";
	/**
	 * 表单解析代理服务
	 */
	public static String FORM_RESOLVERVALUE_PROXY_NAMES = "FormDefinitionResolverProxy";
	/**
	 * Default WidgetVlaueGetter implements Componet Package 
	 */
	public static String FORM_WIDGET_VALUE_DEFAULT_PACKAGE = "com.andpay.timobileframework.mvc.form.getter.componet";
	/**
	 * Default Field Validator implements Componet Package 
	 */
	public static String FORM_FIELD_VALIDATOR_DEFAULT_PACKAGE = "com.andpay.timobileframework.mvc.form.validation.validator";
	/**
	 * Default Field ErrorMsg Descri 
	 */
	public static String FORM_FIELD_ERROR_DEFAULT_PATH = "properties/errorMsg.properties";
}
