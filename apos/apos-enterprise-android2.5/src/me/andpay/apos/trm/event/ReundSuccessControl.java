package me.andpay.apos.trm.event;

import me.andpay.apos.tam.context.TxnControl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

import com.google.inject.Inject;

public class ReundSuccessControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;
	
	public void onClick(Activity activity, FormBean formBean, View v) {
		txnControl.backHomePage(activity);
	}
}
