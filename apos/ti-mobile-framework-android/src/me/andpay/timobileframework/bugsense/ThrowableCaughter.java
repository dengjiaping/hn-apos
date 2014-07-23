package me.andpay.timobileframework.bugsense;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import me.andpay.timobileframework.util.ReflectUtil;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 异常处理对象
 * @author tinyliu
 *
 */
public class ThrowableCaughter extends Handler implements
		UncaughtExceptionHandler {

	private List<ThrowableHandler> handlers;

	private UncaughtExceptionHandler defaultExceptionHandler;

	private boolean isAlwaysThrows;
	/**
	 * 标识消息代码
	 */
	private int ExceptionCallBackMessage = 102;

	ThrowableCaughter(UncaughtExceptionHandler defaultExceptionHandler,
			List<ThrowableHandler> handlers, boolean isAlwaysThrows) {
		super(Looper.getMainLooper());
		this.handlers = handlers;
		this.defaultExceptionHandler = defaultExceptionHandler;
		this.isAlwaysThrows = isAlwaysThrows;
	}

	public void uncaughtException(Thread thread, Throwable ex) {
		ThrowableInfo info = new ThrowableInfo(ex, thread, isAlwaysThrows);
		Log.e("test", "uncaught exception", ex);
		for (ThrowableHandler handler : handlers) {
			if (!handler.isSupport(thread, ex)) {
				continue;
			}
//			this.sendMessage(this.obtainMessage(ExceptionCallBackMessage,
//					new InvokeInfo(handler, info)));
			try {
				handler.processThrowable(info);
			} catch (Throwable th) {
				try {
					ThrowableRecorder.DEFAULT.recordThrowable(info.getEx());
				} catch (Throwable e) {
					
				}
			}

		}
		if (TiAndroidRuntimeInfo.getCurrentActivity() != null
				&& ReflectUtil.isImplInterface(TiAndroidRuntimeInfo
						.getCurrentActivity().getClass(),
						ThrowableHandler.class.getName())) {
			ThrowableHandler handler = (ThrowableHandler) TiAndroidRuntimeInfo
					.getCurrentActivity();
			if (handler.isSupport(thread, ex)) {
				this.sendMessage(this.obtainMessage(
						ExceptionCallBackMessage,
						new InvokeInfo((ThrowableHandler) TiAndroidRuntimeInfo
								.getCurrentActivity(), info)));
			}

		}
		if (defaultExceptionHandler != null && isAlwaysThrows) {
			defaultExceptionHandler.uncaughtException(thread, ex);
		}
	}

	@Override
	public void handleMessage(Message msg) {
		if (msg.what != ExceptionCallBackMessage) {
			return;
		}
		((InvokeInfo) msg.obj).invoke();
	}

	class InvokeInfo {
		ThrowableHandler handler;

		ThrowableInfo info;

		InvokeInfo(ThrowableHandler handler, ThrowableInfo info) {
			this.handler = handler;
			this.info = info;
		}

		public void invoke() {
			try {
				handler.processThrowable(info);
			} catch (Throwable ex) {
				try {
					ThrowableRecorder.DEFAULT.recordThrowable(info.getEx());
				} catch (Throwable e) {
					
				}
			}
		}
	}
}
