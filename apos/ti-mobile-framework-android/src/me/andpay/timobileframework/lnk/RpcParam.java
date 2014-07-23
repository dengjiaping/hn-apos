package me.andpay.timobileframework.lnk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import me.andpay.ti.util.StringUtil;

public class RpcParam {

	public static String service_groups = "service_groups";
	public static String default_address = "default_address";
	public static String address_trust_all = "trustAll";
	public static String connectIdleTime = "connectIdleTime";
	public static String simpleSSLUrl = "simple_ssl_url";
	public static String clientSSLUrl = "client_ssl_url";
	public static String uploadUrl = "upload.url";
	public static String yunUrls = "yun.urls";


	private String split_flag = ",";

	private Map<String, String> serviceGroup = new HashMap<String, String>();

	private AddressInfo addressInfo = new AddressInfo();
	
	private List<String> yunUrlList = new ArrayList<String>();

	public RpcParam(Properties prop) {

		addressInfo.setDefaultAdress(prop.getProperty(default_address));
		addressInfo.setTrustAll(Boolean.valueOf(prop
				.getProperty(address_trust_all)));
		addressInfo.getUrlMap().put(simpleSSLUrl,
				prop.getProperty(simpleSSLUrl));
		addressInfo.getUrlMap().put(clientSSLUrl,
				prop.getProperty(clientSSLUrl));
		addressInfo.setUploadUrl(prop.getProperty(uploadUrl));
		addressInfo.setSslClientUrl(prop.getProperty(clientSSLUrl));
		
		String urlStr = prop.getProperty(yunUrls);
		if(StringUtil.isNotBlank(urlStr)) {
			String[] yunUrlString  = urlStr.split(",");
			for (String url : yunUrlString) {
				yunUrlList.add(url);
			}
		}
	
		
		String[] serviceGroups = prop.getProperty(service_groups).split(
				split_flag);
		Map<String, String> groupMap = new HashMap<String, String>();
		for (String groupName : serviceGroups) {
			String address = prop.getProperty(groupName);
			groupMap.put(groupName, address);
			String idleTime = prop.getProperty(groupName + connectIdleTime);
			if (StringUtil.isNotBlank(idleTime)) {
				addressInfo.getConnectIdleTimes().put(address,
						Integer.valueOf(idleTime));
			}
		}
		addressInfo.setServiceGroups(groupMap);

	}
	

	public Map<String, String> getServiceGroup() {
		return serviceGroup;
	}

	public AddressInfo getAddressInfo() {
		return addressInfo;
	}

	public List<String> getYunUrlList() {
		return yunUrlList;
	}



}
