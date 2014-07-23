package me.andpay.timobileframework.provider;

import java.io.Serializable;

import me.andpay.timobileframework.mvc.form.FormBean;
import android.content.Intent;
import android.os.Bundle;

public class BundleProvider {
	
	/**
	 * intent 序列化对象key
	 */
	public final static String INTENT_DATA_KEY="intent_data_key";

	
	public static Bundle provid(FormBean formBean) {

		Bundle bundle = new Bundle();
		bundle.putSerializable(INTENT_DATA_KEY,(Serializable)formBean.getData());
		
		return bundle;
	}
	
	
	public static <T>  T getFormData(Class<? extends T> clazz,Intent intent) {
		
		Object formData =  intent.getExtras().getSerializable(INTENT_DATA_KEY);
		return clazz.cast(formData);
	}
}
