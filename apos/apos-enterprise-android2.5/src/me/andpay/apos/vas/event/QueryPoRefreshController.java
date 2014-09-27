package me.andpay.apos.vas.event;

import me.andpay.apos.vas.VasProvider;
import me.andpay.apos.vas.activity.PurchaseOrderListActivity;
import me.andpay.apos.vas.callback.DataUpdateCallbackHandler;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class QueryPoRefreshController extends AbstractEventController {

	public void onRefresh(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, true);
	}

	public void onLoadMore(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, false);
	}

	private void loadMoreData(Activity refActivty, FormBean formBean,
			boolean isRefresh) {
		PurchaseOrderListActivity activity = (PurchaseOrderListActivity) refActivty;
		EventRequest request = generateSubmitRequest(refActivty);
		request.open(VasProvider.VAS_DOMAIN_QUERY, activity
				.isHasQueryCondition() ? VasProvider.VAS_ACTION_QUERY_GETPOLIST
				: VasProvider.VAS_ACTION_QUERY_GETPOLISTSTORAGE, Pattern.async);
		if (isRefresh) {
			activity.getCond().setMaxOrderId(null);
			activity.getCond().setMinOrderId(
					activity.getAdapter().getMaxOrderId());
		} else {
			activity.getCond().setMinOrderId(null);
			activity.getCond().setMaxOrderId(
					activity.getAdapter().getMinOrderId());
		}
		request.getSubmitData().put("queryForm", activity.getCond());
		request.callBack(new DataUpdateCallbackHandler(activity, isRefresh));
		request.submit();
	}
}
