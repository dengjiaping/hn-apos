package me.andpay.apos.lam.util;

import me.andpay.ac.consts.ApplicationCodes;
import me.andpay.ac.consts.OSCodes;
import me.andpay.ac.consts.Statuses;
import me.andpay.ac.term.api.base.Languages;
import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.DeviceInfo;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.util.RootUtil;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceInfoUtil {

	public static DeviceInfo setDeviceInfo(TiActivity activity) {

		TelephonyManager tpMg = (TelephonyManager) activity
				.getSystemService(Context.TELEPHONY_SERVICE);
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setImei(tpMg.getDeviceId());
		deviceInfo.setOsVersion(android.os.Build.VERSION.SDK);
		deviceInfo.setOsCode(OSCodes.ANDROID);
		deviceInfo.setAppLanguage(Languages.SIMPLIFIED_CHINESE);

		deviceInfo.setMac(getLocalMacAddress(activity));
		try {
			Integer apkVersion = activity.getPackageManager().getPackageInfo(
					"me.andpay.apos", 0).versionCode;
			deviceInfo.setAppVersionCode(apkVersion.toString());
			deviceInfo.setAppCode(ApplicationCodes.APOS);
		} catch (Exception e) {
			// TODO log
		}
		String deviceId = (String) activity.getAppConfig().getAttribute(
				ConfigAttrNames.DEVICE_ID);
		if (StringUtil.isBlank(deviceId)) {
			deviceId = null;
		}
		deviceInfo.setDeviceId(deviceId);
		deviceInfo.setModel(Build.MODEL);

		String status = Statuses.INVALID;
		if (RootUtil.isRootSystem()) {
			status = Statuses.VALID;
		}

		deviceInfo.setRoot(status);

		activity.getAppContext().setAttribute(RuntimeAttrNames.DEVICE_INFO,
				deviceInfo);

		return deviceInfo;
	}

	/**
	 * 获取mac地址
	 * 
	 * @return
	 */
	public static String getLocalMacAddress(TiActivity activity) {
		WifiManager wifi = (WifiManager) activity
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		if (info != null && StringUtil.isNotBlank(info.getMacAddress())) {
			return info.getMacAddress();
		}
		return "00:00:00:00:00:00";
	}
}
