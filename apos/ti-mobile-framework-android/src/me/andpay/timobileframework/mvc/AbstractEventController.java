package me.andpay.timobileframework.mvc;

import me.andpay.timobileframework.mvc.action.DispatchCenter;
import me.andpay.timobileframework.mvc.context.TiContext;
import me.andpay.timobileframework.mvc.support.TiApplication;
import android.app.Activity;

import com.google.inject.Inject;

public abstract class AbstractEventController implements EventController {

	@Inject
	private DispatchCenter center;

	/**
	 * 预处理方法 在event响应之前调用
	 * 
	 * @param refActivty
	 * @return 是否继续调用事件处理
	 */
	public Boolean preProcess(Activity refActivty) {
		return true;
	}

	protected EventRequest generateSubmitRequest(Activity refActivty) {
		return new AndroidEventRequest(center,
				((TiApplication) refActivty.getApplication())
						.getContextProvider());
	}

	protected TiContext getApplicationConfig(Activity refActivty) {
		return ((TiApplication) refActivty.getApplication())
				.getContextProvider().provider(
						TiContext.CONTEXT_SCOPE_APPLICATION_CONFIG);
	}

	protected TiContext getAppContext(Activity refActivty){
		return ((TiApplication) refActivty.getApplication())
				.getContextProvider().provider(
						TiContext.CONTEXT_SCOPE_APPLICATION);
	}

}
