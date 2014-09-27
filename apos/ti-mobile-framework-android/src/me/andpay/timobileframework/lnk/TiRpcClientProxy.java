package me.andpay.timobileframework.lnk;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import me.andpay.ti.lnk.rpc.RemoteCallExceptionListener;
import me.andpay.ti.lnk.sc.SimpleClientBuilder;
import me.andpay.ti.lnk.sc.SimpleClientConfig;
import me.andpay.ti.lnk.transport.wsock.client.WebSockAddress;
import android.content.Context;

/**
 * Rpc 代理对象
 * 
 * 配置SSL以及重新选择连接地址
 * 
 * @author tinyliu
 * 
 */
public class TiRpcClientProxy implements TiRpcClient {

	private String default_prop_path = "properties";
	private String default_prop_file = "ws_config.properties";
	private String base_file = "base.properties";
	private String base_dev = "base.dev";
	// 测试模式
	private boolean testMode = false;
	private SimpleClientBuilder builder;

	private RpcParam param;

	private AddressInfo conn_address;

	private boolean isConnect = false;

	private boolean isConfigSSL = false;

	private Long selectTimeout = 6 * 1000l;

	private SimpleClientConfig config;

	private TiRemoteParamsReader remoteParamsReader;

	Map<Class<? extends Throwable>, RemoteCallExceptionListener> exceptionListeners = null;

	private Context mContext;

	public TiRpcClientProxy(
			TiTestConnector testConnector,
			Map<Class<? extends Throwable>, RemoteCallExceptionListener> exceptionListeners,
			Context context) {
		this.exceptionListeners = exceptionListeners;
		mContext = context;
	}

	public void loadConfig() {
		Properties prop = new Properties();
		try {
			System.setProperty("java.net.preferIPv6Addresses","false");
			isConfigSSL = false;
			prop.load(Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(
							default_prop_path + File.separator
									+ default_prop_file));
			String baseDev = (String) prop.get(base_dev);
			prop.load(Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream(
							default_prop_path + File.separator + baseDev
									+ File.separator + base_file));

			// 测试模式
			Boolean testModeProp = Boolean.valueOf((String) prop
					.get("test.mode"));
			if (testModeProp){
				testMode = true;
			}
			param = new RpcParam(prop);
			conn_address = param.getAddressInfo();
			
			remoteParamsReader = new TiRemoteParamsReader();
			remoteParamsReader.setRpcParam(param);
			remoteParamsReader.checkRemoteUrlAndChange(mContext);
		} catch (IOException e) {
			throw new RuntimeException("load ws config file happend error", e);
		}

	}

	public String getUploadUrl() {
		return conn_address.getUploadUrl();
	}

	public TiRpcClientProxy() {
		this(null, null, null);
	}

	public void restartTransport() {
		isConfigSSL = false;
	}

	public synchronized void refreshLookupService() {
		builder.refreshLookupService(conn_address.getServiceGroups(), null,
				conn_address.getDefaultAdress());
	}

	public synchronized void start() {
		if (isConnect) {
			return;
		}
		loadConfig();
		startImpl();
	}

	public synchronized void stop() {
		if (!isConnect) {
			return;
		}
		builder.getLightWebSockClient().stop();
		isConnect = false;
		isConfigSSL = false;
	}

	private void startImpl(){
		builder = new SimpleClientBuilder();
		config = new SimpleClientConfig();
		config.setServerAddressByServiceGroup(conn_address.getServiceGroups());
		config.setDefaultServerAddress(conn_address.getDefaultAdress());
		config.setTrustAll(conn_address.getTrustAll());
		config.setExceptionListeners(exceptionListeners);

		for (Entry<String, Integer> iEntry : conn_address.getConnectIdleTimes()
				.entrySet()) {
			config.getMaxConnectionIdleTimes().put(iEntry.getKey(),
					iEntry.getValue());
		}
		// 1异步 0同步
		// config.setAsyncCallMode(1);
		// config.setNetworkStatusChecker(networkStatusChecker);
		config.getCookies().put("test", "test");
		builder.build(config);

		connect(conn_address.getDefaultAdress());
		isConnect = true;
	}

	public void setCookies(Map<String, String> cookies) {
		builder.getRpc().getClientObjectFactory().getClientCookieStorage()
				.getCookies().putAll(cookies);
	}

	public void setCookie(String key, String value) {
		builder.getRpc().getClientObjectFactory().getClientCookieStorage()
				.getCookies().put(key, value);
	}

	public Map<String, String> getCookies() {
		return builder.getRpc().getClientObjectFactory()
				.getClientCookieStorage().getCookies();
	}

	private void disConnect(String address) {
		WebSockAddress webAddress = new WebSockAddress(address);
		builder.getClientTransport().disconnect(webAddress);
	}

	private void connect(String address) {
		WebSockAddress webAddress = new WebSockAddress(address);
		builder.getClientTransport().connect(webAddress);
	}

	public void configSSL(String keyStorePath, String password,
			String keyManagerPassword) {
		builder.getLightWebSockClient().stop();
		builder.getLightWebSockClient().setKeyManagerPassword(
				keyManagerPassword);
		builder.getLightWebSockClient().setKeyStorePassword(password);
		builder.getLightWebSockClient().setKeyStorePath(keyStorePath);
		builder.getLightWebSockClient().setTrustAll(conn_address.getTrustAll());
		builder.getLightWebSockClient().start();
		disConnect(conn_address.getSslClientUrl());

		isConfigSSL = true;
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
		}
	}

	public void pause() {
		if (builder != null) {
			builder.getClientTransport().pause();
		}
	}

	public void resume() {
		if (builder != null) {
			builder.getClientTransport().resume();
		}
	}

	@SuppressWarnings("unchecked")
	public Object getLnkService(Class serviceClass) {
		Object linkService = builder.getRpc().getClientObjectFactory()
				.getClientObject(serviceClass);
		if (linkService == null) {
			System.err.print(serviceClass);
		}
		return linkService;
	}

	public Long getSelectTimeout() {
		return selectTimeout;
	}

	public void setSelectTimeout(Long selectTimeout) {
		this.selectTimeout = selectTimeout;
	}

	public boolean isConn() {
		return isConnect;
	}

	public boolean isConfigSSL() {
		return isConfigSSL;
	}

	public TiRemoteParamsReader getRemoteParamsReader() {
		return remoteParamsReader;
	}

	public void restart() {
		builder.getLightWebSockClient().stop();
		this.startImpl();
	}

}
