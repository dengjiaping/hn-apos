package me.andpay.apos.vas.callback;

import java.util.LinkedList;

import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.activity.PurchaseOrderListActivity;
import me.andpay.apos.vas.activity.adapter.PurchaseOrderListAdapter;
import me.andpay.timobileframework.mvc.ModelAndView;

public class QueryPoListAfterProcessHandler extends AfterProcessWithErrorHandler {

	private PurchaseOrderListActivity activity;

	public QueryPoListAfterProcessHandler(PurchaseOrderListActivity activity) {
		super(activity);
		this.activity = activity;
	}

	@SuppressWarnings("unchecked")
	public void afterRequest(ModelAndView mv) {
		if (activity.getAdapter() != null) {
			activity.getAdapter().destory();
		}
		LinkedList<PurchaseOrderInfo> infos = (LinkedList<PurchaseOrderInfo>) mv
				.getValue("poList");
		if (infos == null || infos.size() == 0) {
			activity.showNoDataView();
		} else {
			activity.showListView();
		}
		synchronized (activity) {
			if (activity.getAdapter() == null) {
				PurchaseOrderListAdapter adapter = initAdapter(infos);
				activity.getRefresh_layout().setAdapter(adapter);
				activity.setAdapter(adapter);
			} else {
				activity.getAdapter().destory();
				activity.getAdapter().addValues(infos);
			}
			activity.getAdapter().notifyDataSetChanged();
			activity.getListView().setSelection(0);
		}
	}

	private PurchaseOrderListAdapter initAdapter(LinkedList<PurchaseOrderInfo> infos) {
		PurchaseOrderListAdapter adapter = new PurchaseOrderListAdapter(activity);
		adapter.addValues(infos);
		return adapter;
	}

	@Override
	protected void refreshAfterNetworkError() {
		activity.showNoDataView();
		activity.queryPoList();
	}

	@Override
	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}

}
