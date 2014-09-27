package me.andpay.apos.lam.event;

import me.andpay.apos.R;
import me.andpay.apos.lam.activity.ActivateCodeActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class ActivateCodeTextWatcherEventControl extends
		AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {

		ActivateCodeActivity codeActivity = (ActivateCodeActivity) activity;
		if (codeActivity.activateCodeText.length() == 6) {
			codeActivity.activateBtn.setEnabled(true);
			codeActivity.solfKeyBoard.getSure_btn().setEnabled(true);
			codeActivity.solfKeyBoard.getSure_btn().setBackgroundDrawable(
					activity.getResources().getDrawable(
							R.drawable.com_keyboard_button_blue_img));

		} else {
			codeActivity.activateBtn.setEnabled(false);
			codeActivity.solfKeyBoard.getSure_btn().setEnabled(false);
			codeActivity.solfKeyBoard.getSure_btn().setBackgroundDrawable(
					activity.getResources().getDrawable(
							R.drawable.com_keyboard_button_img));

		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
