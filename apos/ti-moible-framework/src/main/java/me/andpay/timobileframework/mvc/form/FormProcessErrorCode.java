package me.andpay.timobileframework.mvc.form;

import java.util.HashMap;
import java.util.Map;

/**
 * errorcode定义
 * @author tinyliu
 *
 */
public class FormProcessErrorCode {
	
	private static Map<String, String> errorMsg = new HashMap<String, String>();
	
	static{
		errorMsg.put(FormProcessErrorCode.ERROR_NOTDEFINE, "Cantainer not define @formBind");
		errorMsg.put(FormProcessErrorCode.ERROR_INITFORMBEAN, "FormBean init happend error");
		errorMsg.put(FormProcessErrorCode.ERROR_FIELDVALUSET, "FormBean Field Set value happend error");
		errorMsg.put(FormProcessErrorCode.ERROR_ANDROID_R_INIT, "Reflect Android Static R.id happend error");
		errorMsg.put(FormProcessErrorCode.ERROR_ANDROID_GETRID, "Get Field Android Rid happend error");
	}
	
	/**
	 * 没有定义FormBind
	 */
	public static String ERROR_NOTDEFINE = "P01";
	/**
	 * 初始化FormBean时出现异常
	 */
	
	public static String ERROR_INITFORMBEAN = "P02";
	/**
	 * 设置字段属性值出错
	 */
	public static String ERROR_FIELDVALUSET = "P03";
	
	/**
	 * 反射Android R.id对象时出错
	 */
	public static String ERROR_ANDROID_R_INIT = "P04";
	/**
	 * 获取field对应的ResouceId时出错
	 */
	public static String ERROR_ANDROID_GETRID = "P05";
	/**
	 * 获取field值出错
	 */
	public static String ERROR_FIELDVALUGET = "P06";
	/**
	 * init资源文件出错
	 */
	public static String ERROR_TRANSLATE_INITPROP = "T01";
	
	/**
	 *字段值必须
	 */
	public static String ERROR_VALIDATE_REQUIRED = "V01";
	
	public static String ERROR_VALIDATE_INTEGER = "V02";
	
	public static String ERROR_VALIDATE_DOUBLE = "V03";
	
	public static String ERROR_VALIDATE_COMMONCHAR = "V04";
	
	public static String ERROR_VALIDATE_CHINESECHAR = "V05";
	
	public static String ERROR_VALIDATE_MASK = "V06";
	
	public static String ERROR_VALIDATE_INTEGERRANGE = "V07";
	
	public static String ERROR_VALIDATE_DOUBLERANGE = "V08";
	
	public static String ERROR_VALIDATE_EQUALSFIELD = "V09";
	
	public static String ERROR_VALIDATE_EMAIL = "V10";
	
	public static String ERROR_VALIDATE_PHONENUMBER = "V11";

	public static String ERROR_VALIDATE_STRRANGE = "V12";
	
	public static String ERROR_VALIDATE_IDNO= "V13";
	
	public static String getErrorMsg(String errorCode) {
		return errorMsg.get(errorCode);
	}
	
}
