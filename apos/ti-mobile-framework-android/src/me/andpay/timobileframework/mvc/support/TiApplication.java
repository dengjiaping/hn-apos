package me.andpay.timobileframework.mvc.support;

import me.andpay.timobileframework.mvc.context.TiAndroidContextProvider;
import me.andpay.timobileframework.mvc.context.TiContextProvider;
import android.app.Application;
import android.util.Log;

public class TiApplication extends Application {
	
	private TiContextProvider provider;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(this.getClass().getName(),
				"TiDataContext get Attribute , provider is "
						+ (provider == null));
		provider = new TiAndroidContextProvider(this, this.getClass()
				.getSimpleName());
	}
	

	public TiContextProvider getContextProvider() {
		return provider;
	}

	@Override
	public void onTerminate() {
		Log.i(this.getClass().getName(), "the application has terminate");
		super.onTerminate();
	}

}
