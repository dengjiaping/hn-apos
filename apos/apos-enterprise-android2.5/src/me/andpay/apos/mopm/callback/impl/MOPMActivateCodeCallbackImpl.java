package me.andpay.apos.mopm.callback.impl;

import me.andpay.apos.lam.activity.ActivateCodeActivity;
import me.andpay.apos.lam.callback.ActivateCodeCallback;
import me.andpay.apos.lam.callback.impl.ActivateCodeCallbackImpl;
import me.andpay.apos.lam.callback.impl.LoginCallBackHelper;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class MOPMActivateCodeCallbackImpl extends ActivateCodeCallbackImpl implements ActivateCodeCallback {

	public MOPMActivateCodeCallbackImpl(
			ActivateCodeActivity activateCodeActivity) {
		super(activateCodeActivity);
	}

	public void activateSuccess() {
		activateCodeActivity.submitDialog.cancel();
		LoginCallBackHelper.nextPage(activateCodeActivity);
	}

}
