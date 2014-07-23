package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PurchaseFirstActivity;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class PurClearAllButtonEventControl extends AbstractEventController {
	@Inject
	private TxnControl txnControl;

	public void onClick(Activity activity, FormBean formBean, View view) {

		PurchaseFirstActivity tiActivity = (PurchaseFirstActivity) activity;
		tiActivity.cleanPic();
		tiActivity.clear();
	
	}
}
