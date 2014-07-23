package me.andpay.timobileframework.mvc.context;

import android.app.Application;

/**
 * 上下文提供接口
 * 
 * @author tinyliu
 * 
 */
public class TiAndroidContextProvider implements TiContextProvider {

	private TiAppContext appContext;

	private TiAppConfig appConfig;

	private static TiAndroidContextProvider provider;

	public TiAndroidContextProvider(Application application, String appName) {
		appContext = new TiAppContext(application);
		appConfig = new TiAppConfig(
				application.getSharedPreferences(appName, 0), appName);
	}

	public TiContext provider(int contextScope) {
		switch (contextScope) {
		case TiContext.CONTEXT_SCOPE_APPLICATION:
			return appContext;
		case TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG:
			return appConfig;
		}
		return null;
	}
}
