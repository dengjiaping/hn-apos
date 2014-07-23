package me.andpay.timobileframework.lnk;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class AddressInfo {

	private Map<String, String> serviceGroups = null;

	private String defaultAdress;

	private Boolean trustAll;
	
	private String uploadUrl;
	
	private String sslClientUrl;


	private Map<String, Integer> connectIdleTimes = new HashMap<String, Integer>();

	/**
	 * 地址映射集合
	 */
	private Map<String, String> urlMap = new HashMap<String, String>();

	public Boolean getTrustAll() {
		return trustAll;
	}

	public void setTrustAll(Boolean trustAll) {
		this.trustAll = trustAll;
	}

	public Map<String, String> getServiceGroups() {

		Map<String, String> realServiceGroups = new HashMap<String, String>();
		for (Entry<String, String> iEntry : serviceGroups.entrySet()) {
			realServiceGroups.put(iEntry.getKey(),
					getRealUrl(iEntry.getValue()));
		}
		return realServiceGroups;
	}

	public void setServiceGroups(Map<String, String> serviceGroups) {
		this.serviceGroups = serviceGroups;
	}

	public String getDefaultAdress() {
		return getRealUrl(defaultAdress);
	}

	public void setDefaultAdress(String defaultAdress) {
		this.defaultAdress = defaultAdress;
	}

	public Map<String, Integer> getConnectIdleTimes() {
		return connectIdleTimes;
	}

	public void setConnectIdleTimes(Map<String, Integer> connectIdleTimes) {
		this.connectIdleTimes = connectIdleTimes;
	}

	public Map<String, String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	private String getRealUrl(String urlKey) {
		return getUrlMap().get(urlKey);
	}

	public String getUploadUrl() {
		return uploadUrl;
	}

	public void setUploadUrl(String uploadUrl) {
		this.uploadUrl = uploadUrl;
	}

	public String getSslClientUrl() {
		return urlMap.get(RpcParam.clientSSLUrl);
	}

	public void setSslClientUrl(String sslClientUrl) {
		this.sslClientUrl = sslClientUrl;
	}
	
	
	
}
