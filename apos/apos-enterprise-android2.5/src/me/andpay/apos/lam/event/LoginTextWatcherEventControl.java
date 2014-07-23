package me.andpay.apos.lam.event;

import me.andpay.apos.lam.activity.LoginActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class LoginTextWatcherEventControl extends AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		LoginActivity lactivity = (LoginActivity) activity;
		if (lactivity.userNameText.length() > 0
				&& lactivity.passwordText.length() > 0) {
			lactivity.loginBtn.setEnabled(true);
		} else {
			lactivity.loginBtn.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

}
