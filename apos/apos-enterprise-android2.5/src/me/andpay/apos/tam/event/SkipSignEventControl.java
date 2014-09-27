package me.andpay.apos.tam.event;

import me.andpay.apos.tam.TamProvider;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.google.inject.Inject;

public class SkipSignEventControl extends AbstractEventController {

	@Inject
	private TxnControl txnControl;

	public void onClick(Activity activity, FormBean formBean, View view) {

		Intent intent = new Intent();
		intent.setAction(TamProvider.TAM_TXNDETAIL_ACTIVITY);
		activity.startActivity(intent);
		activity.finish();
	}
}
