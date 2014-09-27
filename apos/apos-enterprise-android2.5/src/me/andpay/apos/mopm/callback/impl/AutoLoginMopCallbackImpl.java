package me.andpay.apos.mopm.callback.impl;

import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.lam.callback.LoginCallback;
import me.andpay.apos.lam.callback.impl.AutoLoginCallback;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.support.TiActivity;

@CallBackHandler
public class AutoLoginMopCallbackImpl extends AutoLoginCallback implements
		LoginCallback {

	public AutoLoginMopCallbackImpl(TiActivity activity) {
		super(activity);
	}

	@Override
	public void loginSuccess(LoginResponse response) {
		OrderPayUtil.goToOrderCheck(activity);
	}

	// TODO更新原理
	@Override
	public void updateApp(String errorCode) {
		loginFaild("当前版本已经停止，请先更新客户端程序");
	}
}
