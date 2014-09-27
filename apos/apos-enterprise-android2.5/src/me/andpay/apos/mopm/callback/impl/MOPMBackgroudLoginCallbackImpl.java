package me.andpay.apos.mopm.callback.impl;

import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.lam.callback.LoginCallback;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class MOPMBackgroudLoginCallbackImpl implements LoginCallback {

	public void loginSuccess(LoginResponse response) {
		// TODO Auto-generated method stub

	}

	public void loginFaild(String errorMsg) {
		// TODO Auto-generated method stub

	}

	public void networkError(String errorMsg) {
		// TODO Auto-generated method stub

	}

	public void updateApp(String errorCode) {
		// TODO Auto-generated method stub

	}

	public void goActivateCert() {
		// TODO Auto-generated method stub

	}

}
