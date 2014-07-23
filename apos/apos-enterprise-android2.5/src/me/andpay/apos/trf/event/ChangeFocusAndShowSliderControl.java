package me.andpay.apos.trf.event;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import me.andpay.apos.common.activity.HomePageActivity;
import me.andpay.apos.trf.activity.PayeeInfomationActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

public class ChangeFocusAndShowSliderControl extends AbstractEventController {
	public void onClick(Activity activity, FormBean formBean, View view) {
		PayeeInfomationActivity payeeActivity = (PayeeInfomationActivity) activity;
		payeeActivity.amtViewLayout.requestFocus();
		InputMethodManager imm = (InputMethodManager) payeeActivity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
		HomePageActivity homeActivity = (HomePageActivity) payeeActivity
				.getParent();
		homeActivity.showSlider();
	}
}
