package me.andpay.timobileframework.mvc;

import me.andpay.timobileframework.bugsense.ThrowableHandler;
import me.andpay.timobileframework.bugsense.ThrowableInfo;
import me.andpay.timobileframework.bugsense.ThrowableSense;
import me.andpay.timobileframework.mvc.action.DispatchCenter;
import me.andpay.timobileframework.mvc.context.TiContextProvider;
import me.andpay.timobileframework.util.ReflectUtil;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncActionTask extends
		AsyncTask<EventRequest, Integer, ModelAndView> {

	private DispatchCenter center;

	private AfterProcessCallBackHandler callBackHander;

	private TiContextProvider provider;

	public AsyncActionTask(DispatchCenter center,
			AfterProcessCallBackHandler callBackHander,
			TiContextProvider provider) {
		this.callBackHander = callBackHander;
		this.center = center;
		this.provider = provider;
	}

	@Override
	protected ModelAndView doInBackground(EventRequest... request) {
		ModelAndView mv = null;
		try {
			mv = center.dipatchEvent(request[0], provider);
		} catch (RuntimeException ex) {
			ThrowableSense.caughtThrowable(Thread.currentThread())
					.uncaughtException(Thread.currentThread(), ex);
			mv = new ModelAndView();
			mv.setHappedEx(ex);
			Log.e(this.getClass().getName(), "AsyncActionTask happend error",
					ex);
		}
		return mv;
	}

	@Override
	protected void onPostExecute(ModelAndView mv) {
		if (callBackHander == null) {
			return;
		}
		if (mv!= null && mv.getHappedEx() != null) {
			if (isCallBackProcessThrowable(mv.getHappedEx())) {
				((ThrowableHandler) callBackHander)
						.processThrowable(new ThrowableInfo(mv.getHappedEx(),
								null, false));
			}
			return;
		} else {
			callBackHander.afterRequest(mv);
		}
	}

	protected boolean isCallBackProcessThrowable(Throwable ex) {
		if (callBackHander == null) {
			return false;
		}
		if (!ReflectUtil.isImplInterface(callBackHander.getClass(),
				ThrowableHandler.class.getName())) {
			return false;
		}
		return ((ThrowableHandler) callBackHander).isSupport(
				Thread.currentThread(), ex);
	}

}
