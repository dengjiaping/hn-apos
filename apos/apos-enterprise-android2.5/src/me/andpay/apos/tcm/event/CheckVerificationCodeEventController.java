package me.andpay.apos.tcm.event;

import android.app.Activity;
import android.text.Editable;
import me.andpay.apos.tcm.activity.SmsChallengeActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

public class CheckVerificationCodeEventController extends
		AbstractEventController {
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		SmsChallengeActivity smsActivity = (SmsChallengeActivity) activity;
		if (smsActivity.inputEditText.length() == 6) {
			smsActivity.nextButton.setEnabled(true);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
