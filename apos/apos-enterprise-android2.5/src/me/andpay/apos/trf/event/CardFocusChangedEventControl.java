package me.andpay.apos.trf.event;

import android.app.Activity;
import android.view.View;
import me.andpay.apos.trf.activity.PayeeInfomationActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;

public class CardFocusChangedEventControl extends AbstractEventController {
	public boolean onFocusChange(Activity activity, FormBean formBean,
			View view, boolean hasFocus) {
		PayeeInfomationActivity payeeActivity = (PayeeInfomationActivity) activity;
		if (!hasFocus) {
			
		}
		return true;
	}
	
	private boolean checkCardNo(String cardNo){
		return false;
	}
}
