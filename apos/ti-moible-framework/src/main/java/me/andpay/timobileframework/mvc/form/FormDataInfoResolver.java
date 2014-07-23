package me.andpay.timobileframework.mvc.form;

import java.util.HashMap;
import java.util.Map;

import me.andpay.timobileframework.mvc.form.info.FormDataInfo;
/**
 * 用于解析表单对象的各类信息
 * @author tinyliu
 *
 */
public class FormDataInfoResolver {
	
	private static Map<Class, FormDataInfo> formDataInfoCaches = new HashMap<Class, FormDataInfo>();
	
	public static FormDataInfo resolver(Class formClass) {
		FormDataInfo formDataInfo = null;
		if(formDataInfoCaches.containsKey(formClass)) {
			formDataInfo = formDataInfoCaches.get(formClass);
		} else {
			formDataInfo = new FormDataInfo(formClass);
			formDataInfoCaches.put(formClass, formDataInfo);
		}
		return formDataInfo;
	}
}	
