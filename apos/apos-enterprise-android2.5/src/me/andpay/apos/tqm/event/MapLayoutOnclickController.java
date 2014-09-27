package me.andpay.apos.tqm.event;

import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.activity.TxnDetailActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MapLayoutOnclickController extends AbstractEventController {

	public void onClick(Activity refActivity, FormBean formBean, View v) {
		TxnDetailActivity activity = (TxnDetailActivity) refActivity;
		PayTxnInfo info = activity.getTxnInfo();
		Intent txnMapIntent = new Intent(
				TqmProvider.TQM_ACTIVITY_DETAIL_MAP_ACTION);
		if (info.getSpecLatitude() == null || info.getSpecLongitude() == 0) {
			txnMapIntent.putExtra("latitude", info.getLatitude());
			txnMapIntent.putExtra("longitude", info.getLongitude());
		} else {
			txnMapIntent.putExtra("latitude", info.getSpecLatitude());
			txnMapIntent.putExtra("longitude", info.getSpecLongitude());
		}

		txnMapIntent.putExtra("locationInfo", info.getTxnAddress());
		activity.startActivity(txnMapIntent);
	}

}
