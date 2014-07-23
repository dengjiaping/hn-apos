package me.andpay.apos.tqm.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.activity.TxnBatchQueryActivity;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class QueryCodButtonClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {
		
		TxnBatchQueryActivity txnBatchQueryActivity = (TxnBatchQueryActivity)refActivty;
		
		Intent txnConditionIntent = new Intent(
				TqmProvider.TQM_ACTIVITY_LIST_CONDITION_ACTION);
		if(txnBatchQueryActivity.getAdapter() != null) {
			byte[] formByte = JacksonSerializer.newPrettySerializer()
					.serialize(txnBatchQueryActivity.getAdapter().getCondition());
			txnConditionIntent.putExtra(
					TqmProvider.TQM_QUERY_COND_FORM,
					formByte);
		}
		refActivty.startActivityForResult(txnConditionIntent,
				TqmProvider.TQM_RESULT_CODE_CONDITION);
	}

	public boolean onLongClick(Activity refActivty, FormBean formBean,View v) {
		final TxnBatchQueryActivity batchQueryActivity = (TxnBatchQueryActivity)refActivty;
		if(batchQueryActivity.getAdapter() == null || !batchQueryActivity.getAdapter().getCondition().isHasViewCond()){
			return false;
		}
		final OperationDialog dialog = new OperationDialog(refActivty,
				ResourceUtil.getString(refActivty, R.string.com_show_str),
				refActivty.getResources().getString(R.string.tqm_condition_clear_str), true);
		
		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				batchQueryActivity.queryAll();
			}
		});
		dialog.show();
		return true;
	}
}
