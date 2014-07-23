package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PostVoucherActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class PostVcEditWatcherEventControl extends AbstractEventController {
	private static final int phoneNumberLenth = 11;

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

		PostVoucherActivity postActivity = (PostVoucherActivity) activity;

		// if (postActivity.emailEditText.length() > 0) {
		//
		// postActivity.postEmailbtn.setEnabled(true);
		// } else {
		// postActivity.postEmailbtn.setEnabled(false);
		// }

		if (postActivity.phoneEditText.length() >= phoneNumberLenth) {

			postActivity.postSmsbtn.setEnabled(true);
		} else {
			postActivity.postSmsbtn.setEnabled(false);
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
		

		PostVoucherActivity postVoucherActivity = (PostVoucherActivity) activity;

		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {

			DisplayMetrics metric = new DisplayMetrics();
			postVoucherActivity.getWindowManager().getDefaultDisplay()
					.getMetrics(metric);

			postVoucherActivity.mainScroll.layout(0, -35, metric.widthPixels,
					Float.valueOf((360 * metric.density)).intValue());
		}
		return false;
	}
}
