package me.andpay.apos.cdriver;

public class DeviceInfo {
	/**
	 * ksn
	 */
	private String ksn;
	/**
	 * 主控密钥是否已经初始化
	 */
	private boolean initMasterKey;
	/**
	 * 密码密钥是否初始化
	 */
	private boolean initPinKey;
	/**
	 * 磁道密钥是否初始化
	 */
	private boolean initDataKey;
	
	private boolean initMacKey;
	
	/**
	 * 错误码
	 */
	private String responseCode;
	
	private String errorMsg;
	
	private boolean success;
	
	/**
	 * 固件版本号
	 */
	private String appVersion;

	public String getKsn() {
		return ksn;
	}

	public void setKsn(String ksn) {
		this.ksn = ksn;
	}

	public boolean isInitMasterKey() {
		return initMasterKey;
	}

	public void setInitMasterKey(boolean initMasterKey) {
		this.initMasterKey = initMasterKey;
	}

	public boolean isInitPinKey() {
		return initPinKey;
	}

	public void setInitPinKey(boolean initPinKey) {
		this.initPinKey = initPinKey;
	}

	public boolean isInitDataKey() {
		return initDataKey;
	}

	public void setInitDataKey(boolean initDataKey) {
		this.initDataKey = initDataKey;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isInitMacKey() {
		return initMacKey;
	}

	public void setInitMacKey(boolean initMacKey) {
		this.initMacKey = initMacKey;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	
	
}
