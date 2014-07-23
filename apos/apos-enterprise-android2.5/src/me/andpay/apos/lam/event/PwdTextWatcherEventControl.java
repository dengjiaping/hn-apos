package me.andpay.apos.lam.event;

import me.andpay.apos.lam.activity.ChangePasswordActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class PwdTextWatcherEventControl extends AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		
		ChangePasswordActivity chActivity = (ChangePasswordActivity)activity;
		if(chActivity.oldPwd.length() > 0 && chActivity.newPassword.length() > 0 && chActivity.rePassword.length() > 0
				&& chActivity.newPassword.length() == chActivity.rePassword.length()) {
			chActivity.changePassword.setEnabled(true);
		}else {
			chActivity.changePassword.setEnabled(false);

		}
		
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

}
