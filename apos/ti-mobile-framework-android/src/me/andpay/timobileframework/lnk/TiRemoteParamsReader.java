package me.andpay.timobileframework.lnk;


import me.andpay.ti.util.JSONObject;
import me.mobile.dexlib.provider.HttpServiveProvider;
import android.content.Context;


/**
 * 远程参数读取
 * 
 * @author cpz
 * 
 */
public class TiRemoteParamsReader {


	private RpcParam rpcParam;

	/**
	 * 检测远程RUL设置是否改变,如果改变更改本地配置
	 */
	public synchronized boolean checkRemoteUrlAndChange(Context context) {
		TiRemoteParams tiRemoteParams = readParams(context);
		if(tiRemoteParams == null) {
			return false;
		}
		if (isUrlChange(tiRemoteParams)) {
			updataRpcParam(tiRemoteParams);
			return true;
		}
		
		return false;
	}

	
	protected void updataRpcParam(TiRemoteParams tiRemoteParams) {
		rpcParam.getAddressInfo().getUrlMap()
				.put(RpcParam.clientSSLUrl, tiRemoteParams.getClientSSLUrl());
		rpcParam.getAddressInfo().getUrlMap()
				.put(RpcParam.simpleSSLUrl, tiRemoteParams.getSimpleSSLUrl());
		rpcParam.getAddressInfo().setUploadUrl(tiRemoteParams.getUploadUrl());

	}

	private boolean isUrlChange(TiRemoteParams tiRemoteParams) {
		String clientSSLUrl = rpcParam.getAddressInfo().getUrlMap()
				.get(RpcParam.clientSSLUrl);
		String simpleSSLUrl = rpcParam.getAddressInfo().getUrlMap()
				.get(RpcParam.simpleSSLUrl);
		String uploadUrl = rpcParam.getAddressInfo().getUploadUrl();

		if (!clientSSLUrl.equals(tiRemoteParams.getClientSSLUrl())) {
			return true;
		}
		if (!simpleSSLUrl.equals(tiRemoteParams.getSimpleSSLUrl())) {
			return true;
		}

		if (!uploadUrl.equals(tiRemoteParams.getUploadUrl())) {
			return true;
		}

		return false;
	}

	private TiRemoteParams readParams(Context context) {
		for (String url : rpcParam.getYunUrlList()) {
			String data = HttpServiveProvider.get(context).httpSimpleGet(url) ;
			if (data != null) {
				TiRemoteParams tiRemoteParams = jsonConvert(data);
				if (tiRemoteParams != null) {
					return tiRemoteParams;
				}
			}
		}
		return null;

	}

	private TiRemoteParams jsonConvert(String json) {

		TiRemoteParams tiRemoteParams = new TiRemoteParams();
		try {
			JSONObject jsonObject = new JSONObject(json);
			jsonObject = (JSONObject) jsonObject.get("primary");
			tiRemoteParams.setClientSSLUrl(jsonObject
					.getString("client_ssl_url"));
			tiRemoteParams.setSimpleSSLUrl(jsonObject
					.getString("simple_ssl_url"));
			tiRemoteParams
					.setUploadUrl(jsonObject.getString("file_server_url"));
			return tiRemoteParams;
		} catch (Exception e) {
			return null;
		}
	}



	public RpcParam getRpcParam() {
		return rpcParam;
	}

	public void setRpcParam(RpcParam rpcParam) {
		this.rpcParam = rpcParam;
	}

}
