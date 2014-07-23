package me.andpay.timobileframework.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWorkUtil {
	
	
	/**
	 * 是否开启wifi
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isOpenWifi(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifi = connectivity
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return wifi.isAvailable();
	}

	/**
	 * 检查当前网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
			if (netInfo != null && netInfo.isConnectedOrConnecting()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前链接的网络名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getCurrentNetworkType(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return null;
		} else {
			NetworkInfo[] infos = connectivity.getAllNetworkInfo();
			if (infos != null) {
				for (int i = 0; i < infos.length; i++) {
					if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
						infos[i].getTypeName();
					}
				}
			}
		}
		return null;
	}

	/**
	 * 是否是高速网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isHighNetWork(Context context) {
		ConnectivityManager cManager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);

		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			return false;
		}

		// wifi是为高速网
		if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}

		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		/**
		 * NETWORK_TYPE_HSDPA 移动3g NETWORK_TYPE_UMTS 联通3g NETWORK_TYPE_EVDO_0
		 * 电信3g NETWORK_TYPE_EVDO_A 电信3g
		 * 
		 */
		int netWorkType = tm.getNetworkType();
		if (netWorkType == TelephonyManager.NETWORK_TYPE_HSDPA
				|| netWorkType == TelephonyManager.NETWORK_TYPE_UMTS
				|| netWorkType == TelephonyManager.NETWORK_TYPE_EVDO_0
				|| netWorkType == TelephonyManager.NETWORK_TYPE_EVDO_A) {

			return true;
		}

		return false;
	}
	
	
	public static boolean isWifi(Context context) {
		
		ConnectivityManager cManager = (ConnectivityManager) context
				.getApplicationContext().getSystemService(
						Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		
		return false;

	}
}
