package me.andpay.timobileframework.lnk;

public class TiRemoteParams {
	
	/**
	 * 上传地址
	 */
	public String uploadUrl;
	/**
	 * 单项ssl地址
	 */
	private String simpleSSLUrl;
	
	/**
	 * 双向SSL地址
	 */
	private String clientSSLUrl;

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getSimpleSSLUrl() {
		return simpleSSLUrl;
	}

	public void setSimpleSSLUrl(String simpleSSLUrl) {
		this.simpleSSLUrl = simpleSSLUrl;
	}

	public String getClientSSLUrl() {
		return clientSSLUrl;
	}

	public void setClientSSLUrl(String clientSSLUrl) {
		this.clientSSLUrl = clientSSLUrl;
	}
	
	
	
}
