package me.andpay.timobileframework.lnk;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import me.andpay.ti.lnk.rpc.RemoteCallExceptionListener;
import me.andpay.ti.lnk.transport.wsock.client.NetworkStatusChecker;

import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TiRpcClientProvider implements Provider<TiRpcClient> {

	private Class<? extends TiTestConnector> testConnector;

	private TiRpcClient client;

	public Map<Class<? extends Throwable>, RemoteCallExceptionListener> exceptionListeners = new HashMap<Class<? extends Throwable>, RemoteCallExceptionListener>();

	@Inject
	private NetworkStatusChecker networkStatusChecker;

	@Inject
	private Application application;

	private TiRpcClientProvider(Class<? extends TiTestConnector> testConnector) {
		this.testConnector = testConnector;
	}

	public synchronized TiRpcClient get() {
		if (client == null) {
			client = new TiRpcClientProxy(null, exceptionListeners,
					application.getApplicationContext());
		}
		return client;
	}

	public void registerExceptionListeners(
			Class<? extends Throwable> throwableClass,
			RemoteCallExceptionListener exceptionListener) {
		this.exceptionListeners.put(throwableClass, exceptionListener);
	}

	public static TiRpcClientProvider getProvider(
			Class<? extends TiTestConnector> testConnector) {
		return new TiRpcClientProvider(testConnector);
	}

	public NetworkStatusChecker getNetworkStatusChecker() {
		return networkStatusChecker;
	}

}
