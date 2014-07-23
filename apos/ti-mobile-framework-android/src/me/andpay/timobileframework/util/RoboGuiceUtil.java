package me.andpay.timobileframework.util;

import android.content.Context;
import roboguice.RoboGuice;

public class RoboGuiceUtil {

	public static <T> T getInjectObject(Class<? extends T> clazz,
			Context context) {

		Object obj = RoboGuice.getInjector(context).getInstance(clazz);
		return clazz.cast(obj);
	}
}
