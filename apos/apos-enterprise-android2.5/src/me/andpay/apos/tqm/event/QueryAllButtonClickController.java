package me.andpay.apos.tqm.event;

import me.andpay.apos.tqm.activity.TxnBatchQueryActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class QueryAllButtonClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		TxnBatchQueryActivity activity = (TxnBatchQueryActivity) refActivty;
		activity.queryAll(); 
	}
}
