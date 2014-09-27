package me.andpay.apos.vas.event;

import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.PurchaseOrderListActivity;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;

/**
 * 列表点击事件
 * 
 * @author tinyliu
 * 
 */
public class PurchaseOrderItemClickController extends AbstractEventController {

	public void onItemClick(Activity refActivty, FormBean formBean,
			AdapterView<?> adapterView, View view, int position, long id) {
		PurchaseOrderListActivity activity = (PurchaseOrderListActivity) refActivty;
		if (position >= activity.getAdapter().getCount()) {
			return;
		}
		PurchaseOrderInfo info = (PurchaseOrderInfo) activity.getAdapter()
				.getItem(position);
		activity.getControl().setFlowContextData(info);
		activity.getControl().nextSetup(activity,
				VasProvider.VAS_FLOWS_QUERY_COMPLETE_DETAIL);
	}
}
