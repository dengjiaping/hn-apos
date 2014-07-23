package me.andpay.apos.vas.event;

import me.andpay.apos.R;
import me.andpay.apos.cmview.OperationDialog;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.PurchaseOrderListActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;

public class PurQueryCodButtonClickController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {

		PurchaseOrderListActivity queryActivity = (PurchaseOrderListActivity) refActivty;

		queryActivity.getControl().setFlowContextData(queryActivity.getCond());
		queryActivity.getControl().nextSetup(queryActivity,
				VasProvider.VAS_FLOWS_QUERY_COMPLETE_COND);
	}

	public boolean onLongClick(Activity refActivty, FormBean formBean, View v) {
		final PurchaseOrderListActivity queryActivity = (PurchaseOrderListActivity) refActivty;
		if (queryActivity.getAdapter() == null || !queryActivity.isHasQueryCondition()) {
			return false;
		}
		final OperationDialog dialog = new OperationDialog(refActivty,
				ResourceUtil.getString(refActivty, R.string.com_show_str), refActivty
						.getResources().getString(R.string.tqm_condition_clear_str), true);

		dialog.setSureButtonOnclickListener(new OnClickListener() {
			public void onClick(View v) {
				dialog.dismiss();
				queryActivity.clearCondition();
				queryActivity.queryAll();
			}
		});
		dialog.show();
		return true;
	}
}
