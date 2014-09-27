package me.andpay.apos.trm.event;

import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.trm.activity.RefundBatchQueryActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class RefundTxnItemClickController extends AbstractEventController {

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {
		if (position >= ((RefundBatchQueryActivity) refActivty).getAdapter()
				.getCount()) {
			return;
		}
		PayTxnInfo info = (PayTxnInfo) ((RefundBatchQueryActivity) refActivty)
				.getAdapter().getItem(position);
		Intent txnDetailIntent = new Intent(
				TqmProvider.TQM_ACTIVITY_DETAIL_ACTION);
		txnDetailIntent.putExtra("txnId", info.getTxnId());
		txnDetailIntent.putExtra("isRefundQuery", true);
		refActivty.startActivity(txnDetailIntent);
	}

}
