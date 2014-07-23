package me.andpay.apos.tqm.event;

import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.activity.TxnBatchQueryActivity;
import me.andpay.apos.tqm.callback.DataUpdateCallbackHandler;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class QueryBatchTxnRefreshController extends AbstractEventController {

	public void onRefresh(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, true);
	}

	public void onLoadMore(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, false);
	}

	private void loadMoreData(Activity refActivty, FormBean formBean,
			boolean isRefresh) {
		TxnBatchQueryActivity activity = (TxnBatchQueryActivity) refActivty;
		EventRequest request = generateSubmitRequest(refActivty);
		request.open(
				TqmProvider.TQM_DOMAIN_QUERY,
				activity.isHasQueryCondition() ? TqmProvider.TQM_ACTION_QUERY_GETTXNLIST
						: TqmProvider.TQM_ACTION_QUERY_GETTXNLIST_STORAGE,
				Pattern.async);
		if (isRefresh) {
			activity.getAdapter().getCondition().setMaxTxnId(null);
			activity.getAdapter().getCondition()
					.setMinTxnId(activity.getAdapter().getMaxTxnId());
		} else {
			activity.getAdapter().getCondition().setMinTxnId("");
			activity.getAdapter().getCondition()
					.setMaxTxnId(activity.getAdapter().getMinTxnId());
		}
		request.getSubmitData().put("counts", TqmProvider.TQM_CONST_MAX_COUNTS);
		request.getSubmitData().put("queryConditionForm",
				activity.getAdapter().getCondition());
		request.callBack(new DataUpdateCallbackHandler(activity, isRefresh));
		request.submit();
	}

}
