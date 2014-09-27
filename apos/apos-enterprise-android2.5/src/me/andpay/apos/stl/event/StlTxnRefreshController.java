package me.andpay.apos.stl.event;

import me.andpay.apos.stl.action.QuerySettleAction;
import me.andpay.apos.stl.activity.StlTxnQueryActivity;
import me.andpay.apos.stl.callback.impl.StlTxnDataUpdateCallbackHandler;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class StlTxnRefreshController extends AbstractEventController {

	public void onRefresh(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, true);
	}

	public void onLoadMore(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, false);
	}

	private void loadMoreData(Activity refActivty, FormBean formBean,
			boolean isRefresh) {
		StlTxnQueryActivity activity = (StlTxnQueryActivity) refActivty;
		EventRequest request = generateSubmitRequest(refActivty);
		request.open(QuerySettleAction.DOMAIN_NAME,
				QuerySettleAction.QUERY_TXN, Pattern.async);
		if (isRefresh) {
			activity.getAdapter().getCondition().setMaxTxnId(null);
			activity.getAdapter().getCondition()
					.setMinTxnId(activity.getAdapter().getMaxTxnId());
		} else {
			activity.getAdapter().getCondition().setMinTxnId("");
			activity.getAdapter().getCondition()
					.setMaxTxnId(activity.getAdapter().getMinTxnId());
		}
		request.getSubmitData().put("queryConditionForm",
				activity.getAdapter().getCondition());
		request.callBack(new StlTxnDataUpdateCallbackHandler(activity,
				isRefresh));
		request.submit();
	}
}
