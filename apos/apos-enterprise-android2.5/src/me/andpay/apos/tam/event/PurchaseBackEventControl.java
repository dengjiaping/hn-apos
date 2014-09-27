package me.andpay.apos.tam.event;

import me.andpay.apos.common.util.BackUtil;
import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class PurchaseBackEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {

		PurchaseFirstActivity purActivity = (PurchaseFirstActivity) activity;
		BackUtil.showBackDialog(purActivity);

	}
}
