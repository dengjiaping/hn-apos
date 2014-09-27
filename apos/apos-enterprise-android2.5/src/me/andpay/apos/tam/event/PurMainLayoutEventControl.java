package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class PurMainLayoutEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		PurchaseFirstActivity tiActivity = (PurchaseFirstActivity) activity;
		tiActivity.solfKeyBoard.hideKeyboard();
	}
}
