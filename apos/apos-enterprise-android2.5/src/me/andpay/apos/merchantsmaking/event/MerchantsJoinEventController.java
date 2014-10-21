package me.andpay.apos.merchantsmaking.event;

import me.andpay.apos.merchantsmaking.activity.MerchantsJoinActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class MerchantsJoinEventController extends AbstractEventController {
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		MerchantsJoinActivity cptivity = (MerchantsJoinActivity) activity;
		if (cptivity.name.length() > 0 && cptivity.typeMc.length() > 0
				&& cptivity.adress.length() > 0

				&& cptivity.introduce.length() > 0
				&& cptivity.phoneNumber.length() > 0) {
			cptivity.commit.setEnabled(true);
		} else {
			cptivity.commit.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
