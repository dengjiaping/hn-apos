package me.andpay.apos.opm.event;

import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.apos.opm.action.QueryOrderAction;
import me.andpay.apos.opm.activity.OrderPayListActivity;
import me.andpay.apos.opm.callback.impl.UpdateOrderDateCallbackImpl;
import me.andpay.apos.opm.form.QueryOrderCondForm;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class QueryOrderRefreshController extends AbstractEventController {

	public void onRefresh(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, true);
	}

	public void onLoadMore(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, false);
	}

	private void loadMoreData(Activity refActivty, FormBean formBean,
			boolean isRefresh) {

		OrderPayListActivity activity = (OrderPayListActivity) refActivty;

		EventRequest request = generateSubmitRequest(refActivty);
		request.open(QueryOrderAction.QUERY_ORDER_ACTION,
				QueryOrderAction.QUERY_ORDER_PAY, Pattern.async);
		QueryOrderCondForm queryCond = activity.orderPayListAdapter
				.getQueryOrderCondForm();
		if (isRefresh) {
			if (queryCond.getStatus().equals(OrderRecord.STATUS_WAITING_PAY)) {
				queryCond.setMaxId(null);
				queryCond.setMinId(activity.orderPayListAdapter.getMinId());
				queryCond.setOrders("orderRecordId-");
			} else {
				queryCond.setMaxTxnId(null);
				queryCond.setMinTxnId(activity.orderPayListAdapter
						.getMinTxnId());
				queryCond.setOrders("txnId-");
			}
		} else {
			if (queryCond.getStatus().equals(OrderRecord.STATUS_WAITING_PAY)) {
				queryCond.setMinId(null);
				queryCond.setMaxId(activity.orderPayListAdapter.getMaxId());
				queryCond.setOrders("orderRecordId-");
			} else {
				queryCond.setMinTxnId(null);
				queryCond.setMaxTxnId(activity.orderPayListAdapter
						.getMaxTxnId());
				queryCond.setOrders("txnId-");
			}
		}
		request.getSubmitData().put("counts", TqmProvider.TQM_CONST_MAX_COUNTS);
		request.getSubmitData().put("orderQueryForm",
				activity.orderPayListAdapter.getQueryOrderCondForm());
		request.callBack(new UpdateOrderDateCallbackImpl(activity, isRefresh));
		request.submit();
	}

}
