package me.andpay.apos.opm.event;

import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.opm.OpmProvider;
import me.andpay.apos.opm.activity.OrderPayListActivity;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

public class QueryClickItemController extends AbstractEventController {
	

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {
		if(position >= ((OrderPayListActivity) refActivty).orderPayListAdapter.getCount()) {
			return;
		}
		OrderInfo info =(OrderInfo) ((OrderPayListActivity) refActivty).orderPayListAdapter
				.getItem(position);
		Intent orderDetailIntent = new Intent(OpmProvider.OPM_ORDERDETAIL_ACTIVITY);
		byte[] infoByte = JacksonSerializer.newPrettySerializer().serialize(
				info.getClass(), info);
		orderDetailIntent.putExtra("orderInfo", infoByte);
		refActivty.startActivity(orderDetailIntent);
	}
}
