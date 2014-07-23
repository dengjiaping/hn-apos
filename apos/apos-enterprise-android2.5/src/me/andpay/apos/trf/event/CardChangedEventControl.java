package me.andpay.apos.trf.event;

import me.andpay.apos.trf.activity.PayeeInfomationActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

public class CardChangedEventControl extends AbstractEventController {
	private static final int SESSION_LENGTH = 4;
	private static final String DELIMITER = " ";

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		String inputCardNo = s.toString();
		if (inputCardNo.endsWith(DELIMITER))
			return;
		PayeeInfomationActivity payeeActivity = (PayeeInfomationActivity) activity;
		String tempNo = inputCardNo.replace(DELIMITER, "");
		if (tempNo.length() % SESSION_LENGTH == 0) {
			inputCardNo = new StringBuffer(inputCardNo).append(DELIMITER)
					.toString();
			payeeActivity.cardEditTextView.setText(inputCardNo);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

}
