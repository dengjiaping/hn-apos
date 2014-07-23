package me.andpay.timobileframework.mvc.support;

import me.andpay.timobileframework.mvc.AndroidEventRequest;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.action.DispatchCenter;
import roboguice.RoboGuice;
import roboguice.activity.RoboActivity;
import roboguice.activity.event.OnCreateEvent;
import roboguice.event.EventManager;
import roboguice.inject.RoboInjector;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

public class TiInitActivity  extends RoboActivity{
	

	public Context context;
	
	public RoboInjector injector;
	
	
	public TiInitActivity(Context context) {
		this.context =context;
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
		injector = RoboGuice.getInjector(context);
        eventManager = injector.getInstance(EventManager.class);
        injector.injectMembersWithoutViews(context);
        eventManager.fire(new OnCreateEvent(savedInstanceState));
//        this.finish();
	}
	
	public EventRequest generateSubmitRequest(Activity refActivty) {
		return new AndroidEventRequest(injector.getInstance(DispatchCenter.class),
				((TiApplication) refActivty.getApplication()).getContextProvider());
	}
}
