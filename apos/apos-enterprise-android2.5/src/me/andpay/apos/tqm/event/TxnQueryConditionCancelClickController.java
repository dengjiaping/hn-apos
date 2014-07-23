package me.andpay.apos.tqm.event;

import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class TxnQueryConditionCancelClickController extends
		AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		refActivty.setResult(Activity.RESULT_CANCELED);
		refActivty.finish();
	}
}
