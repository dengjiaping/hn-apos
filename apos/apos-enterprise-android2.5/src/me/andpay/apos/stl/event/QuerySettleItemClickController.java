package me.andpay.apos.stl.event;

import me.andpay.ac.term.api.settle.SettleOrder;
import me.andpay.apos.stl.StlProvider;
import me.andpay.apos.stl.activity.SettleListActivity;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class QuerySettleItemClickController extends AbstractEventController {

 
	

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {
		if (position >= ((SettleListActivity) refActivty).settleListAdapter.getCount()) {
			return;
		}
		SettleOrder settleOrder = (SettleOrder) ((SettleListActivity) refActivty).settleListAdapter
				.getItem(position);
		Intent txnDetailIntent = new Intent(StlProvider.STL_ACTIVITY_DETAIL);
		byte[] infoByte = JacksonSerializer.newPrettySerializer().serialize(
				settleOrder.getClass(), settleOrder);
		txnDetailIntent.putExtra("settleOrder", infoByte);
		refActivty.startActivity(txnDetailIntent);
	}
}
