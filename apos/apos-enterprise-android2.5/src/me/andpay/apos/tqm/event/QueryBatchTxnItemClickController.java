package me.andpay.apos.tqm.event;

import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.activity.TxnBatchQueryActivity;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.google.inject.Inject;

public class QueryBatchTxnItemClickController extends AbstractEventController {

	@Inject
	PayTxnInfoDao dao;

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {
		if (position >= ((TxnBatchQueryActivity) refActivty).getAdapter().getCount()) {
			return;
		}
		PayTxnInfo info = (PayTxnInfo) ((TxnBatchQueryActivity) refActivty).getAdapter()
				.getItem(position);
		Intent txnDetailIntent = new Intent(TqmProvider.TQM_ACTIVITY_DETAIL_ACTION);
		byte[] infoByte = JacksonSerializer.newPrettySerializer().serialize(
				info.getClass(), info);
		txnDetailIntent.putExtra("payTxnInfo", infoByte);
		refActivty.startActivity(txnDetailIntent);
	}
}
