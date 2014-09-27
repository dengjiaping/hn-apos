package me.andpay.apos.lft.even;

import me.andpay.apos.lft.activity.PayeeInformationActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;
import android.view.View;

/**
 * 输入卡号监听器
 * 
 * @author Administrator
 * 
 */
public class PayeeBankNumberTextWatcherEventControl extends
		AbstractEventController {
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		PayeeInformationActivity lactivity = (PayeeInformationActivity) activity;
		if (lactivity.number.length() > 0) {

			// lactivity.number.setPadding(5,0,5,0);
			lactivity.cardNumberTitle.setVisibility(View.GONE);
		} else {
			// lactivity.number.setPadding(20,0,20,0);
			lactivity.cardNumberTitle.setVisibility(View.VISIBLE);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
