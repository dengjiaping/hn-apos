package me.andpay.apos.tqm.event;

import me.andpay.apos.R;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.tqm.activity.TxnBatchQueryActivity;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class QueryBatchStatusButtonClickController extends
		AbstractEventController {
	public void onClick(Activity refActivty, FormBean formBean, View v) {
		TxnBatchQueryActivity activity = (TxnBatchQueryActivity) refActivty;
		if (activity.getSelectedStatusId() != null
				&& activity.getSelectedStatusId() == v.getId()) {
			return;
		}
		QueryConditionForm form = null;
		if (activity.getAdapter() != null) {
			form = activity.getAdapter().getCondition();
		} else {
			form = new QueryConditionForm();
		}

		switch (v.getId()) {
		case R.id.tqm_txn_list_all_btn:
			form.setStatus(null);
			activity.setHasQueryCondition(false);
			activity.setSelectedStatus(null);
			break;
		case R.id.tqm_txn_list_succ_btn:
			form.setStatus(PayTxnInfoStatus.STATUS_SUCCESS);
			activity.setHasQueryCondition(true);
			activity.setSelectedStatus(PayTxnInfoStatus.STATUS_SUCCESS);
			break;
		case R.id.tqm_txn_list_failed_btn:
			form.setStatus(PayTxnInfoStatus.STATUS_FAIL);
			activity.setHasQueryCondition(true);
			activity.setSelectedStatus(PayTxnInfoStatus.STATUS_FAIL);
			break;
		}
		activity.setSelectedStatusId(v.getId());
		activity.changeStatusButton();
		form.setMaxTxnId(null);
		form.setMinTxnId(null);
		activity.setSelectedStatusId(v.getId());
		activity.queryBatchTxn(form);
	}

}
