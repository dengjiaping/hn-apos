package me.andpay.apos.mopm.callback.impl;

import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.lam.activity.LoginActivity;
import me.andpay.apos.lam.callback.LoginCallback;
import me.andpay.apos.lam.callback.impl.LoginCallbackImpl;
import me.andpay.apos.mopm.order.OrderPayContext;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;


@CallBackHandler
public class MOPMLoginCallbackImpl extends LoginCallbackImpl implements LoginCallback {
	
	public MOPMLoginCallbackImpl(LoginActivity activity) {
		super(activity);
	}

	public void loginSuccess(LoginResponse response) {
		activity.loginDialog.cancel();
		OrderPayContext orderPayContext = OrderPayUtil
				.getOrderPayContext();
		orderPayContext.setNeedAutoLogin(true);
		OrderPayUtil.goToOrderCheck(activity);
	}

	//TODO更新原理
	public void updateApp(String errorCode) {
		activity.loginDialog.cancel();
		loginFaild("当前版本已经停止，请先更新客户端程序");
	}
}
