package me.andpay.apos.opm.event;

import me.andpay.apos.opm.activity.InputOrderNoActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class OrderNoTextEventControl extends AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		InputOrderNoActivity inputOrderNoActivity = (InputOrderNoActivity) activity;

		if (inputOrderNoActivity.orderNoText.length() > 0) {
			inputOrderNoActivity.queryBtn.setEnabled(true);
		} else {
			inputOrderNoActivity.queryBtn.setEnabled(false);
		}

	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
