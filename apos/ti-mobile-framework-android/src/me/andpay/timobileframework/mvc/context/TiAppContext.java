package me.andpay.timobileframework.mvc.context;

import android.app.Application;

/**
 * 应用全局上下文
 * Scope：应用程序生命周期
 * @author tinyliu
 *
 */
public class TiAppContext extends TiDataContext {
	
	public TiAppContext(Application application) {
		super(application);
	}

	@Override
	public int getScope() {
		return TiContext.CONTEXT_SCOPE_APPLICATION;
	}
}
