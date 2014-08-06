package me.andpay.apos.lft.even;

import me.andpay.apos.lft.activity.TopupActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class TopupTextWatcherEventControl extends AbstractEventController {
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		TopupActivity lactivity = (TopupActivity) activity;
		if (lactivity.phoneNumber.length() > 0 && lactivity.amount.length() > 0) {
			lactivity.sure.setEnabled(true);
		} else {
			lactivity.sure.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
