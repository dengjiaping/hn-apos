package me.andpay.apos.stl.event;

import me.andpay.apos.stl.action.QuerySettleAction;
import me.andpay.apos.stl.activity.SettleListActivity;
import me.andpay.apos.stl.callback.impl.UpdateSettleCallbackImpl;
import me.andpay.apos.stl.form.QuerySettleCondForm;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class QuerySettleRefreshController extends AbstractEventController {

	public void onRefresh(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, true);
	}

	public void onLoadMore(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, false);
	}

	private void loadMoreData(Activity refActivty, FormBean formBean,
			boolean isRefresh) {

		SettleListActivity activity = (SettleListActivity) refActivty;

		EventRequest request = generateSubmitRequest(refActivty);
		request.open(QuerySettleAction.DOMAIN_NAME,
				QuerySettleAction.QUERY_SETTLE, Pattern.async);
		QuerySettleCondForm queryCond = activity.settleListAdapter
				.getQuerySettleCondForm();
		if (isRefresh) {
			queryCond.setMaxSettleTime(null);
			queryCond.setMinSettleTime(activity.settleListAdapter
					.getMaxSettleTime());
			queryCond.setOrders("settleTime-");
		} else {
			queryCond.setMinSettleTime(null);
			queryCond.setMaxSettleTime(activity.settleListAdapter
					.getMinSettleTime());
			queryCond.setOrders("settleTime-");

		}
		request.getSubmitData().put("settleQueryForm",
				activity.settleListAdapter.getQuerySettleCondForm());
		request.callBack(new UpdateSettleCallbackImpl(activity, isRefresh));
		request.submit();
	}
}
