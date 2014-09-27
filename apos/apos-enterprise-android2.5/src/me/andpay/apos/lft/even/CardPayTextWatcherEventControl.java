package me.andpay.apos.lft.even;

import me.andpay.apos.lft.activity.CreditCardPaymentsActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

/**
 * 信用卡还款事件控制器
 * 
 * @author Administrator
 *
 */
public class CardPayTextWatcherEventControl extends AbstractEventController {
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		CreditCardPaymentsActivity cptivity = (CreditCardPaymentsActivity) activity;
		if (cptivity.money.length() > 0 && cptivity.number.length() > 0) {
			cptivity.sure.setEnabled(true);
		} else {
			cptivity.sure.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
