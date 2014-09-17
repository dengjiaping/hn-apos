package me.andpay.timobileframework.mvc.support;

import me.andpay.timobileframework.mvc.context.TiAndroidContextProvider;
import me.andpay.timobileframework.mvc.context.TiContextProvider;
import android.app.Application;
import android.content.Context;
import android.util.Log;

public class TiApplication extends Application {
	
	private TiContextProvider provider;
	private static Context context;

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(this.getClass().getName(),
				"TiDataContext get Attribute , provider is "
						+ (provider == null));
		provider = new TiAndroidContextProvider(this, this.getClass()
				.getSimpleName());
		context  = getApplicationContext();
		
	}
	

	public TiContextProvider getContextProvider() {
		return provider;
	}

	@Override
	public void onTerminate() {
		Log.i(this.getClass().getName(), "the application has terminate");
		super.onTerminate();
	}
	
	public static Context getContext(){
		return context;
	}

}
