package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.RepostVoucherActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;
import android.view.MotionEvent;
import android.view.View;

public class RepostVcEditWatcherEventControl extends AbstractEventController {
	private static final int phoneNumberLenth = 11;

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

		RepostVoucherActivity repostActivity = (RepostVoucherActivity) activity;

		if (repostActivity.phoneEditText.length() >= phoneNumberLenth) {
			repostActivity.sendBtn.setEnabled(true);
		} else {
			repostActivity.sendBtn.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {
		return false;
	}
}
