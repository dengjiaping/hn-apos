package me.andpay.apos.merchantsmaking.event;

import me.andpay.apos.merchantsmaking.activity.CouponsActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class CouponsEventController extends AbstractEventController {
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		CouponsActivity cptivity = (CouponsActivity) activity;
		if (cptivity.cardNumber.length() > 0
				&& cptivity.phoneNumber.length() > 0) {
			cptivity.couponsSure.setEnabled(true);
		} else {
			cptivity.couponsSure.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
