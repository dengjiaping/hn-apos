package me.andpay.apos.stl.callback.impl;

import java.util.LinkedList;

import me.andpay.apos.R;
import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.stl.activity.StlTxnQueryActivity;
import me.andpay.apos.tqm.activity.TxnListAdapter;
import me.andpay.timobileframework.mvc.ModelAndView;

public class StlTxnDataUpdateCallbackHandler extends
		AfterProcessWithErrorHandler {

	private TxnListAdapter adapter;

	private boolean isRefresh;

	private StlTxnQueryActivity refActivty;

	public StlTxnDataUpdateCallbackHandler(StlTxnQueryActivity refActivty,
			boolean isRefresh) {
		super(refActivty);
		this.adapter = refActivty.getAdapter();
		this.isRefresh = isRefresh;
		this.refActivty = refActivty;
	}

	@Override
	public void afterRequest(ModelAndView mv) {
		LinkedList<PayTxnInfo> infos = (LinkedList<PayTxnInfo>) mv
				.getValue("txnList");
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
				R.string.tqm_txn_list_no_refresh_str) : String.format(
				refActivty.getApplicationContext().getString(
						R.string.tqm_txn_list_refresh_str), size);

		ToastTools.topToast(refActivty, str, ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void addDataByRefreshFlag(LinkedList<PayTxnInfo> infos) {
		for (int i = 1; i <= infos.size(); i++) {
			if (isRefresh) {
				adapter.addValue(infos.get(infos.size() - i), true);
			} else {
				adapter.addValue(infos.get(i - 1), false);
			}
		}
	}

	private void onLoad(LinkedList<PayTxnInfo> infos) {
		if (isRefresh) {
			showPrompt(infos.size());
		}
		if (infos.size() > 0) {
			synchronized (refActivty) {
				addDataByRefreshFlag(infos);
				adapter.notifyDataSetChanged();
			}
		}
		/*
		 * if (infos.size() < TqmProvider.TQM_CONST_MAX_COUNTS) {
		 * refActivty.getRefresh_layout().setPullLoadEnable(false); }
		 */

	}

	@Override
	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}

}