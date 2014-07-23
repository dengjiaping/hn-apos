package me.andpay.apos.lam.callback.impl;

import me.andpay.ac.term.api.auth.LoginResponse;
import me.andpay.apos.R;
import me.andpay.apos.cmview.PromptDialog;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.lam.activity.ActivateCodeActivity;
import me.andpay.apos.lam.callback.ActivateCodeCallback;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class ActivateCodeCallbackImpl implements ActivateCodeCallback {

	protected ActivateCodeActivity activateCodeActivity;

	public ActivateCodeCallbackImpl(ActivateCodeActivity activateCodeActivity) {
		this.activateCodeActivity = activateCodeActivity;
	}

	public void activateSuccess() {
		activateCodeActivity.finish();
		activateCodeActivity.submitDialog.cancel();
		LoginResponse loginResponse = (LoginResponse) activateCodeActivity
				.getAppContext().getAttribute(RuntimeAttrNames.LOGIN_RESPONSE);

	
		if(LoginCallBackHelper.passwordExpire(loginResponse,activateCodeActivity)) {
			return;
		}

		LoginCallBackHelper.nextPage(activateCodeActivity);
	}

	public void activateFaild(String errorMsg) {
		// 失败对话框
		activateCodeActivity.submitDialog.cancel();
		PromptDialog dialog = new PromptDialog(this.activateCodeActivity,
				activateCodeActivity.getResources().getString(
						R.string.lam_activate_error_str), errorMsg);
		dialog.show();

	}

}
