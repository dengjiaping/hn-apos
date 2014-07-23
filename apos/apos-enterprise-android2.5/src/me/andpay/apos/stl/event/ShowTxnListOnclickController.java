package me.andpay.apos.stl.event;

import me.andpay.apos.stl.StlProvider;
import me.andpay.apos.stl.activity.SettleDetailActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class ShowTxnListOnclickController extends AbstractEventController {
	
	

	public void onClick(Activity activity, FormBean formBean, View view) {
	
		SettleDetailActivity settleDetailActivity = (SettleDetailActivity)activity;
		Intent txnDetailIntent = new Intent(StlProvider.STL_ACTIVITY_TXN_LIST);
		txnDetailIntent.putExtra("settleOrderId", settleDetailActivity.settleOrder.getSettleOrderId());
		settleDetailActivity.startActivity(txnDetailIntent);
	}
}
