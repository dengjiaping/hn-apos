package me.andpay.apos.trf.event;

import me.andpay.apos.trf.activity.PayeeInfomationActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class AmtTouchEventControl extends AbstractEventController {
	public boolean onTouch(Activity activity, FormBean formBean, View view,
			MotionEvent motionEvent) {
		if (!view.hasFocus()) {
			view.requestFocus();
		}
		return true;

	}

	public boolean onFocusChange(Activity activity, FormBean formBean,
			View view, boolean hasFocus) {
		PayeeInfomationActivity payeeActivity = (PayeeInfomationActivity) activity;
		if (hasFocus) {
			fixCursor(payeeActivity);
		}
		return true;
	}

	private void fixCursor(PayeeInfomationActivity payeeActivity) {
		String text = payeeActivity.amtEditTextView.getText().toString();
		int textLength = text == null ? 0 : text.length();
		payeeActivity.amtEditTextView.setSelection(textLength);
		((InputMethodManager) payeeActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(
				payeeActivity.amtEditTextView, 0);
	}
}
