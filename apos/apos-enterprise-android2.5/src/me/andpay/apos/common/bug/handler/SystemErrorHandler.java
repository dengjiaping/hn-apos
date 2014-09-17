package me.andpay.apos.common.bug.handler;

import me.andpay.apos.common.bug.ThrowableReporter;
import me.andpay.timobileframework.bugsense.ThrowableHandler;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import android.app.Application;
import android.os.Looper;

import com.google.inject.Inject;

/**
 * 记录所有UI主线程未捕获异常 记录所有Error级别异常
 * 
 * @author tinyliu
 * 
 */
public class SystemErrorHandler implements ThrowableHandler {

	@Inject
	private ThrowableReporter throwableReporter;
	
	@Inject
	private Application application;

	public void processThrowable(ThrowableInfo info) {
//		throwableReporter.submitError(info.getEx());
		
//		StatService.reportException(application, info.getEx());
		TiAndroidRuntimeInfo.setup(null);
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public boolean isSupport(Thread thread, Throwable ex) {
		if (thread == Looper.getMainLooper().getThread()) {
			return true;
		}
		return false;
	}
}
