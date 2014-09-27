package me.andpay.apos.opm.event;

import me.andpay.apos.R;
import me.andpay.apos.opm.activity.OrderPayListActivity;
import me.andpay.apos.opm.form.QueryOrderCondForm;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;

public class QueryStatusSelectorController extends AbstractEventController {

	public void onClick(Activity refActivty, FormBean formBean, View v) {

		OrderPayListActivity activity = (OrderPayListActivity) refActivty;

		QueryOrderCondForm form = activity.orderPayListAdapter
				.getQueryOrderCondForm();
		if (form == null) {
			form = new QueryOrderCondForm();
		}

		if (v.getId() == R.id.opm_order_nopay_btn) {
			activity.statusSelectorButtonIndext = 0;
		} else if (v.getId() == R.id.opm_order_haspay_btn) {
			activity.statusSelectorButtonIndext = 1;
		}
		activity.changeStatusButton();

		form.setMaxId(null);
		form.setMinId(null);
		form.setMinTxnId(null);
		form.setMaxTxnId(null);
		activity.changeStatus(form);
		activity.sendQueryForm(form);
	}

}
