package me.andpay.apos.common.util;

import android.content.Context;
import android.content.Intent;

public class ResourceUtil {

	public static String getString(Context context, int sourceId) {
		return context.getResources().getString(sourceId);
	}

	public static String getIntentStr(Intent intent, String key) {

		if (intent == null || intent.getExtras() == null) {
			return "";
		}

		Object obj = intent.getExtras().get(key);
		if (obj == null) {
			return "";
		}
		return obj.toString();

	}

	public static boolean getIntentBoolean(Intent intent, String key) {

		if (intent == null || intent.getExtras() == null) {
			return false;
		}

		Object obj = intent.getExtras().get(key);
		if (obj == null) {
			return false;
		}
		return Boolean.valueOf(obj.toString());

	}
}
