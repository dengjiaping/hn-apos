package me.andpay.timobileframework.runtime;

import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Looper;
import android.telephony.TelephonyManager;

public class TiAndroidRuntimeInfo {

	
	private static boolean isInit = false;

	private static String IMEI;

	private static String mobile_model;

	private static String app_version;

	private static String sdk_version;

	private static String os_version;

	private static String file_path;

	private static String app_package;
	
	private static String deviceId;
	
	private static Stack<Activity> activityStack = new Stack<Activity>();
	
	public static void popActitvity(Activity activity) {
		activityStack.remove(activity);
	}
	

	public static void setup(Activity activity) {

		activityStack.push(activity);
		if (!isInit) {
			TelephonyManager tm = (TelephonyManager) activity
					.getSystemService(activity.TELEPHONY_SERVICE);
			IMEI = tm.getDeviceId();
			mobile_model = Build.MODEL;
			sdk_version = Build.VERSION.SDK;// SDK版本号
			os_version = Build.VERSION.RELEASE;// Firmware/OS 版本号

			file_path = activity.getFilesDir().getAbsolutePath();
			
			PackageManager pm = activity.getPackageManager();
			try {
				PackageInfo pi;
				// Version
				pi = pm.getPackageInfo(activity.getPackageName(), 0);
				app_version = String.valueOf(pi.versionCode);
				// Package name
				app_package = pi.packageName;

			} catch (NameNotFoundException e) {

			}
			isInit = true;
		}
	}

	/**
	 * 获取应用版本
	 * 
	 * @return
	 */
	public static String getAppVersion() {
		return app_version;
	}

	/**
	 * 获取手机IMEI号码
	 * 
	 * @return
	 */
	public static String getIMEI() {
		return IMEI;
	}

	/**
	 * 获取手机MAC
	 * 
	 * @return
	 */
	public static String getWifiMac() {

		WifiManager wifi = (WifiManager) activityStack.peek()
				.getSystemService(Context.WIFI_SERVICE);

		WifiInfo info = wifi.getConnectionInfo();

		return info.getMacAddress();
	}

	/**
	 * 获取操作系统版本号
	 * 
	 * @return
	 */
	public static String getOsVersion() {
		return os_version;
	}

	/**
	 * 获取手机型号
	 * 
	 * @return
	 */
	public static String getMobileModel() {
		return mobile_model;
	}

	/**
	 * 获取应用私有路径
	 * 
	 * @return
	 */
	public static String getAppFilePath() {
		return file_path;
	}

	/**
	 * 获取应用包名
	 * 
	 * @return
	 */
	public static String getAppPackage() {
		return app_package;
	}

	/**
	 * 获取sdk version
	 */
	public static String getSdkVersion() {
		return sdk_version;
	}

	public static Activity getCurrentActivity() {
		if(activityStack.isEmpty()) {
			return null;
		}
		return activityStack.peek();
	}

	public static boolean isUIMainThread(Thread thread) {
		return Looper.getMainLooper().getThread() == thread;
	}


	public static Stack<Activity> getActivityStack() {
		return activityStack;
	}


	public static String getDeviceId() {
		return deviceId;
	}


	public static void setDeviceId(String deviceId) {
		TiAndroidRuntimeInfo.deviceId = deviceId;
	}
	
	

}
