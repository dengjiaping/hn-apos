package me.andpay.apos.lft.even;

import android.app.Activity;
import android.text.Editable;
import me.andpay.apos.lft.activity.PayWaterCostActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
/**
 * 缴水费事件控制器
 * @author Administrator
 *
 */
public class PayWaterTextWatcherEventControl extends AbstractEventController{
	public void onTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int before, int count) {
		PayWaterCostActivity payWater = (PayWaterCostActivity) activity;
		if (payWater.city.length() > 0 && payWater.company.length() > 0
				&&payWater.customer.length()>0
				) {
			payWater.next.setEnabled(true);
		} else {
			payWater.next.setEnabled(false);
		}
	}

	public void beforeTextChanged(Activity activity, FormBean formBean,
			CharSequence s, int start, int count, int after) {

	}

	public void afterTextChanged(Activity activity, FormBean formBean,
			Editable s) {

	}

}
