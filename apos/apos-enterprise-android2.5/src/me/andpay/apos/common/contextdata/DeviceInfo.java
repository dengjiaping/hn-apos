package me.andpay.apos.common.contextdata;

import me.andpay.apos.common.constant.ConfigAttrNames;
import me.andpay.ti.util.StringUtil;

import com.crashlytics.android.Crashlytics;

/**
 * 设备信息
 * 
 * @author cpz
 * 
 */
public class DeviceInfo {

	/**
	 * 设备编号
	 */
	private String deviceId;

	/**
	 * 设备名称
	 */
	private String deviceName;

	/**
	 * 秘钥存储密码
	 */
	private String keyStorePassword;
	/**
	 * 
	 */
	private String imei;

	/**
	 * MAC地址
	 */
	private String mac;

	/**
	 * 设备操作系统类型
	 */
	private String osCode;

	/**
	 * 操作系统版本
	 */
	private String osVersion;

	/**
	 * 应用版本
	 */
	private String appVersionCode;
	/**
	 * 应用类型
	 */
	private String appCode;

	/**
	 * 语言类型
	 */
	private String appLanguage;
	/**
	 * 设备型号
	 */
	private String model;

	/**
	 * 是否root
	 */
	private String root;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		if (StringUtil.isNotBlank(deviceId)) {
			Crashlytics.setString(ConfigAttrNames.DEVICE_ID, deviceId);
		}
		this.deviceId = deviceId;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getOsCode() {
		return osCode;
	}

	public void setOsCode(String osCode) {
		this.osCode = osCode;
	}

	public String getAppVersionCode() {
		return appVersionCode;
	}

	public void setAppVersionCode(String appVersionCode) {
		this.appVersionCode = appVersionCode;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getAppLanguage() {
		return appLanguage;
	}

	public void setAppLanguage(String appLanguage) {
		this.appLanguage = appLanguage;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

}
