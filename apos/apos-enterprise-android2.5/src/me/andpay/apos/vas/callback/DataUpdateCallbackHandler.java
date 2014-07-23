package me.andpay.apos.vas.callback;

import java.util.LinkedList;

import me.andpay.apos.R;
import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.vas.activity.PurchaseOrderListActivity;
import me.andpay.apos.vas.activity.adapter.PurchaseOrderListAdapter;
import me.andpay.timobileframework.mvc.ModelAndView;

public class DataUpdateCallbackHandler extends AfterProcessWithErrorHandler {

	private PurchaseOrderListAdapter adapter;

	private boolean isRefresh;

	private PurchaseOrderListActivity refActivty;

	public DataUpdateCallbackHandler(PurchaseOrderListActivity refActivty,
			boolean isRefresh) {
		super(refActivty);
		this.adapter = refActivty.getAdapter();
		this.isRefresh = isRefresh;
		this.refActivty = refActivty;
	}

	public void afterRequest(ModelAndView mv) {
		LinkedList<PurchaseOrderInfo> infos = (LinkedList<PurchaseOrderInfo>) mv
				.getValue("poList");
		stopListView();
		onLoad(infos);
	}

	@Override
	public void processNetworkError() {
		stopListView();
		showError();
	}

	@Override
	protected void processOtherError() {
		stopListView();
		super.processOtherError();
	}

	private void stopListView() {
		if (isRefresh) {
			refActivty.getRefresh_layout().stopRefresh();
		} else {
			refActivty.getRefresh_layout().stopLoadMore();
		}
	}

	private void showError() {
		ToastTools.topToast(refActivty,
				ResourceUtil.getString(refActivty, R.string.com_net_error_str),
				ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void showPrompt(int size) {
		String str = size == 0 ? refActivty.getApplicationContext().getString(
				R.string.tqm_txn_list_no_refresh_str) : String.format(refActivty
				.getApplicationContext().getString(R.string.tqm_txn_list_refresh_str),
				size);

		ToastTools.topToast(refActivty, str, ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void addDataByRefreshFlag(LinkedList<PurchaseOrderInfo> infos) {
		for (int i = 1; i <= infos.size(); i++) {
			if (isRefresh) {
				adapter.addValue(infos.get(infos.size() - i), true);
			} else {
				adapter.addValue(infos.get(i - 1), false);
			}
		}
	}

	private void onLoad(LinkedList<PurchaseOrderInfo> infos) {
		if (isRefresh) {
			showPrompt(infos.size());
		}
		if (infos.size() > 0) {
			synchronized (refActivty) {
				addDataByRefreshFlag(infos);
				adapter.notifyDataSetChanged();
			}
		}

	}

	@Override
	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}

}