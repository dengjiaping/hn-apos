package me.andpay.apos.common.action;

import me.andpay.apos.common.callback.SessionKeepCallback;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.lam.LamProvider;
import me.andpay.apos.lam.action.LoginAction;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.action.MultiAction;
import me.andpay.timobileframework.util.ReflectUtil;
import android.app.Application;

import com.google.inject.Inject;

/**
 * 用于保持
 * 
 * @author tinyliu
 * 
 */
public class SessionKeepAction extends MultiAction {

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	Application application;

	@Inject
	private LoginAction loginAction;

	@Override
	public ModelAndView handler(ActionRequest request) throws RuntimeException {
		if (!tiRpcClient.isConfigSSL()) {
			try {
				loginAction.reLogin(request);
			} catch (Throwable throwable) {
				if (request.getHandler() != null
						&& ReflectUtil.isImplInterface(request.getHandler()
								.getClass(), SessionKeepCallback.class
								.getName())) {
					SessionKeepCallback sessionCallback = (SessionKeepCallback) request
							.getHandler();
					if (ErrorMsgUtil.isNetworkError(throwable)) {
						sessionCallback.networkError(ErrorMsgUtil.parseError(
								application, throwable));
					} else {
						sessionCallback.loginFaild(ErrorMsgUtil.parseError(
								application, throwable));
					}
					return null;
				}
			}

			// ModelAndView mv = relogin(request);
			// if (mv.getHappedEx() != null
			// && request.getHandler() != null
			// && ReflectUtil.isImplInterface(request.getHandler()
			// .getClass(), SessionKeepCallback.class.getName())) {
			// SessionKeepCallback sessionCallback = (SessionKeepCallback)
			// request
			// .getHandler();
			// if (ErrorMsgUtil.isNetworkError(mv.getHappedEx())) {
			// sessionCallback.networkError(ErrorMsgUtil.parseError(
			// application, mv.getHappedEx()));
			// } else {
			// sessionCallback.loginFaild(ErrorMsgUtil.parseError(
			// application, mv.getHappedEx()));
			// }
			// return null;
			// }
		}
		return super.handler(request);
	}

	private ModelAndView relogin(ActionRequest request) {
		return request.getDispatcher().forward(LamProvider.LAM_DOMAIN_LOGIN,
				LamProvider.LAM_ACTION_LOGIN_RELOGIN, request);
	}

}
