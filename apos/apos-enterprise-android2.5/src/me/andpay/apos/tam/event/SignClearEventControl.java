package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.SignActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class SignClearEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		final SignActivity signActivity = (SignActivity) activity;

		signActivity.signClear();

	}
}
