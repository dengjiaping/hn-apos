package me.andpay.apos.lft.even;

import android.app.Activity;
import android.text.Editable;
import me.andpay.apos.lft.activity.PayeeInformationActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

/**
 * 转账收款人事件控制器
 * 
 * @author Administrator
 * 
 */
public class PayeeIfmTextWatcherEventControl extends AbstractEventController {

	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		PayeeInformationActivity lactivity = (PayeeInformationActivity) activity;
		if (lactivity.money.length() > 0 && lactivity.number.length() > 0) {
			lactivity.sure.setEnabled(true);
		} else {
			lactivity.sure.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
