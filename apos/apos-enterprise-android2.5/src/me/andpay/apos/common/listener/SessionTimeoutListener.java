package me.andpay.apos.common.listener;

import java.lang.reflect.Method;

import me.andpay.ac.term.api.base.AppExcCodes;
import me.andpay.apos.common.util.ErrorMsgUtil;
import me.andpay.apos.lam.LamProvider;
import me.andpay.apos.lam.action.LoginAction;
import me.andpay.ti.base.AppRtException;
import me.andpay.ti.lnk.rpc.ExceptionListenerContext;
import me.andpay.ti.lnk.rpc.NextStep;
import me.andpay.ti.lnk.rpc.RemoteCallExceptionListener;
import me.andpay.timobileframework.lnk.TiRpcClient;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.support.TiActivity;
import me.andpay.timobileframework.runtime.TiAndroidRuntimeInfo;
import me.andpay.timobileframework.util.NetWorkUtil;
import android.app.Application;

import com.google.inject.Inject;

public class SessionTimeoutListener implements RemoteCallExceptionListener {

	@Inject
	private TiRpcClient tiRpcClient;

	@Inject
	private Application application;

	private Throwable relogin() {
		TiActivity tiActivity = (TiActivity) TiAndroidRuntimeInfo
				.getCurrentActivity();
		EventRequest request = tiActivity.generateSubmitRequest(tiActivity);
		request.open(LamProvider.LAM_DOMAIN_LOGIN,
				LamProvider.LAM_ACTION_LOGIN_RELOGIN, Pattern.sync);
		ModelAndView modelView = request.submit();
		Object expObj = modelView.getValue(LoginAction.EXCEPTION);
		if (expObj == null) {
			return null;
		}

		return (Throwable) expObj;
	}

	public NextStep onException(Method method, Object[] args,
			Throwable throwable, ExceptionListenerContext ctx) {

		if (throwable != null && throwable instanceof AppRtException) {
			AppRtException appBizException = (AppRtException) throwable;
			if (AppExcCodes.SESSION_TIMEOUT.equals(appBizException.getCode())) {

				Throwable loginExp = relogin();
				if (loginExp == null) {
					return NextStep.recall();
				} else {
					return NextStep.throwException(loginExp);
				}
				// 无效客户端证书，暂时重启应用
			} else if (AppExcCodes.INVALID_CLIENT_CERT.equals(appBizException
					.getCode())) {
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		}
		if (ErrorMsgUtil.isNetworkError(throwable)
				&& NetWorkUtil.isNetworkConnected(application)) {

			if (tiRpcClient.getRemoteParamsReader().checkRemoteUrlAndChange(application.getApplicationContext())) {
				tiRpcClient.refreshLookupService();
			}

		}

		return NextStep.throwException(throwable);
	}
}
