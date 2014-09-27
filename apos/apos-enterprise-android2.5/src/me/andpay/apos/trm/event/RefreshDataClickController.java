package me.andpay.apos.trm.event;

import me.andpay.apos.trm.activity.RefundBatchQueryActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class RefreshDataClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		RefundBatchQueryActivity refundBatchQueryActivity = (RefundBatchQueryActivity) refActivty;
		refundBatchQueryActivity.queryBatchTxn(refundBatchQueryActivity
				.getAdapter().getCondition());
	}
}
