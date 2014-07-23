package me.andpay.timobileframework.util;

import java.lang.reflect.Method;

import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.content.Intent;

/**
 * Intent数据转换
 * 
 * @author cpz
 * 
 */
public class IntentDataConvert {

	/**
	 * Intent数据转成对象
	 * 
	 * @param clazz
	 * @param intent
	 * @return
	 */
	public static <T> T intentToObject(Class<? extends T> clazz, Intent intent) {

		boolean hasDate = false;
		try {
			if (intent == null) {
				return clazz.newInstance();
			}

			T obj = clazz.newInstance();
			Method[] methods = clazz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				String methodName = method.getName();
				if (methodName.startsWith("set")) {
					String keyName = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);
					Object value = intent.getExtras().get(keyName);
					if (value != null) {
						hasDate = true;
						method.invoke(obj, value);
					}
				}
			}

			if (hasDate) {
				return obj;
			}
			;
			return null;
		} catch (Exception ex) {
			return null;
		}
	}

	public static <T> T getActivityContext(Class<? extends T> clazz,
			Intent intent) {
		T obj = TiFlowControlImpl.instanceControl().getFlowContextData(clazz);
		if (obj == null) {
			obj = intentToObject(clazz, intent);
		}
		return obj;
	}
}
