/*
 * Copyright 2009 Michael Burton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package me.andpay.timobileframework.mvc.support;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import me.andpay.timobileframework.bugsense.ThrowableSense;
import me.andpay.timobileframework.mvc.AndroidEventRequest;
import me.andpay.timobileframework.mvc.EventDelegateProcess;
import me.andpay.timobileframework.mvc.EventGenerate;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.action.DispatchCenter;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import roboguice.RoboGuice;
import roboguice.activity.RoboTabActivity;
import roboguice.util.RoboContext;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class TiTabActivity extends RoboTabActivity implements RoboContext, EventGenerate  {
	@Inject
	private EventDelegateProcess delegateProcess = null;

	private Injector injector = null;

	@Inject
	private DispatchCenter center = null;

	public EventRequest generateSubmitRequest(Activity refActivty) {
		return new AndroidEventRequest(center,
				((TiApplication) refActivty.getApplication())
						.getContextProvider());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			injector = RoboGuice.getBaseApplicationInjector(this.getApplication());
			TiAndroidRuntimeInfo.setup(this);
			ThrowableSense.caughtThrowable(Thread.currentThread());
			for (Field field : this.getClass().getDeclaredFields()) {
				processDelegateEvent(field, injector);
			}

		} catch (Exception e) {
			throw new RuntimeException("process delegate happend error", e);

		}
	}

	public TiApplication getTiApplication() {
		return (TiApplication) this.getApplication();
	}

	public TiContext getAppContext() {
		return getTiApplication().getContextProvider().provider(
				TiContext.CONTEXT_SCOPE_APPLICATION);
	}

	public TiContext getAppConfig() {
		return getTiApplication().getContextProvider().provider(
				TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
	}

	private void processDelegateEvent(Field field, Injector injector)
			throws IllegalArgumentException, IllegalAccessException {
		for (Annotation anno : field.getAnnotations()) {
			if (anno instanceof EventDelegate) {
				EventDelegate delegate = (EventDelegate) anno;
				field.setAccessible(true);
				delegateProcess.delegate(this, (View) field.get(this),
						delegate, injector);  
			}
			if (anno instanceof EventDelegateArray) {
				EventDelegateArray delegates = (EventDelegateArray) anno;
				field.setAccessible(true);
				for (EventDelegate delegate : delegates.value()) {
					delegateProcess.delegate(this, (View) field.get(this),
							delegate, injector);
				}
			}

		}
	}
	
	
	

	@Override
	protected void onDestroy() {
		TiAndroidRuntimeInfo.setup(null);
		super.onDestroy();
		if (this.scopedObjects != null) {
			this.scopedObjects.clear();
			this.scopedObjects = null;
		}
		Log.i(this.getClass().getName(), "ti activity ondestory");
		System.gc();
	}

}
