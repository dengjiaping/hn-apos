package me.andpay.apos.lft.even;

import me.andpay.apos.lft.activity.PayElectricityCostActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.text.Editable;

/**
 * 缴电费事件控制器
 * 
 * @author Administrator
 * 
 */
public class PayElectricityTextWatcherEventControl extends
		AbstractEventController {
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		PayElectricityCostActivity payElectricity = (PayElectricityCostActivity) activity;
		if (payElectricity.city.length() > 0 && payElectricity.company.length() > 0
				&&payElectricity.customer.length()>0
				) {
			payElectricity.next.setEnabled(true);
		} else {
			payElectricity.next.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}
}
