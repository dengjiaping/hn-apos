package me.andpay.apos.config;

import me.andpay.timobileframework.config.TiMobileModule;
import android.util.Log;

public class EventListenerModule  extends TiMobileModule {

	@Override
	protected void configure() {
		Log.i("EventListenerModule loader", "EventListenerModule configure call");
	}

}
