package me.andpay.apos.tam.event;

import me.andpay.apos.tam.activity.PostVoucherActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class NewTxnEventControl extends AbstractEventController {

	public void onClick(Activity activity, FormBean formBean, View view) {
		PostVoucherActivity postVoucherActivity = (PostVoucherActivity) activity;
		postVoucherActivity.txnControl.backHomePage(postVoucherActivity);
	}

}
