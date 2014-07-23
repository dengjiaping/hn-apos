package me.andpay.apos.common.util;

import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.app.Activity;
import android.content.SharedPreferences;

import com.crashlytics.android.Crashlytics;

public class CrashLyticsUtil {
	
	/**
	 * 初始化crash
	 */
	public static void initCrashLytics(Activity activity) {
	
	   Crashlytics.start(activity.getApplicationContext());
	   SharedPreferences sharedPreferences =  activity.getSharedPreferences(TiApplication.class.getSimpleName(), 0);
	   String deviceId =   sharedPreferences.getString(ConfigAttrNames.DEVICE_ID, null);
	   Crashlytics.setString(ConfigAttrNames.DEVICE_ID, deviceId);
	}
	
//	public static void logSubmit(String log) {
//		Crashlytics.log(log);
//		Crashlytics.logException(new Exception());
//	}
//	
//	public static void logSubmit(String log,Throwable tr) {
//		Crashlytics.log(log);
//		Crashlytics.logException(tr);
//	}
}
