package me.andpay.apos.common.constant;

import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiApplication;

import com.google.inject.Inject;

import android.app.Application;

public class AposContext {

	@Inject
	private Application application;

	private static TiContext appConfig;
	private static TiContext appContext;

	public TiContext getAppConfig() {
		if (appConfig != null) {
			return appConfig;
		}

		TiApplication tiApplication = (TiApplication) application;

		appConfig = tiApplication.getContextProvider().provider(
				TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);

		return appConfig;
	}

	public TiContext getAppContext() {
		if (appContext != null) {
			return appContext;
		}

		TiApplication tiApplication = (TiApplication) application;
		appContext = tiApplication.getContextProvider().provider(
				TiContext.CONTEXT_SCOPE_APPLICATION);
		return appContext;
	}
}
