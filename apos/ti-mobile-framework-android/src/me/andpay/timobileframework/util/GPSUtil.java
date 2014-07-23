package me.andpay.timobileframework.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * android GPS 工具类
 * 
 * @author tinyliu
 * 
 */
public class GPSUtil {


	
	/**
	 * 判断当前GPS是否打开
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isGPSOpen(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
	}
	
	


}
