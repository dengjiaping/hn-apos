package me.andpay.apos.ssm.event;

import me.andpay.apos.ssm.activity.SettleInfoSendActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class TextWatcherController extends AbstractEventController {

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {
		// TODO Auto-generated method stub

	}

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

		SettleInfoSendActivity postActivity = (SettleInfoSendActivity) activity;

		if (postActivity.phoneEditText.length() > 0
				|| postActivity.mailEditText.length() > 0) {

			postActivity.ssm_batch_send_btn.setEnabled(true);
		} else {
			postActivity.ssm_batch_send_btn.setEnabled(false);
		}

	}
}
