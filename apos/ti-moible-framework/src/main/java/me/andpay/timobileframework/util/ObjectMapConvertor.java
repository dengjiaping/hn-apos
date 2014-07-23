package me.andpay.timobileframework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ObjectMapConvertor {

	/**
	 * 把对象转换成map
	 * 
	 * @param bean
	 * @return
	 */
	public static Map<String, String> describe(Object bean) {

		try {
			Map<String, String> mapValue = new HashMap<String, String>();
			Class<?> cls = bean.getClass();
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields) {
				String name = field.getName();
				String strGet = "get" + name.substring(0, 1).toUpperCase()
						+ name.substring(1, name.length());
				Method methodGet = cls.getDeclaredMethod(strGet);
				Object object = methodGet.invoke(bean);

				String value = object != null ? object.toString() : null;
				if(value !=null && value.trim().length() >0 ) {
					mapValue.put(name, value);
				}
			}
			return mapValue;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}
}
