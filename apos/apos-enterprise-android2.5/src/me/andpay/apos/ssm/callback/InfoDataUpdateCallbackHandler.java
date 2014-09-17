package me.andpay.apos.ssm.callback;

import java.util.LinkedList;

import me.andpay.apos.R;
import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.ssm.activity.SettleInfoActivity;
import me.andpay.apos.ssm.activity.SsmBaseAdapter;
import me.andpay.timobileframework.mvc.ModelAndView;

public class InfoDataUpdateCallbackHandler extends AfterProcessWithErrorHandler {

	private SsmBaseAdapter adapter;

	private boolean isRefresh;

	private SettleInfoActivity refActivty;

	public InfoDataUpdateCallbackHandler(SettleInfoActivity refActivty,
			boolean isRefresh) {
		super(refActivty);
		this.adapter = refActivty.getAdapter();
		this.isRefresh = isRefresh;
		this.refActivty = refActivty;
		
	}

	@Override
	public void afterRequest(ModelAndView mv) {
		LinkedList<PayTxnInfo> infos = (LinkedList<PayTxnInfo>) mv
				.getValue("infoList");
		stopListView();
		onLoad(infos);
	}

	@Override
	public void processNetworkError() {
		stopListView();
		showError();
	}

	private void stopListView() {
		if (isRefresh) {
			refActivty.getRefresh_layout().stopRefresh();
		} else {
			refActivty.getRefresh_layout().stopLoadMore();
		}
	}

	private void addDataByRefreshFlag(LinkedList<PayTxnInfo> infos) {
		for (int i = 1; i <= infos.size(); i++) {
			if (isRefresh) {
				adapter.getRerords().addFirst(infos.get(infos.size() - i));
			} else {
				adapter.getRerords().addLast(infos.get(i - 1));
			}
		}
	}

	private void onLoad(LinkedList<PayTxnInfo> infos) {
		if (isRefresh) {
			showPrompt(infos.size());
		}
		if (infos.size() > 0) {
			addDataByRefreshFlag(infos);
			refActivty.getRecordCounts().setText(
					"" + adapter.getRerords().size());
			adapter.notifyDataSetChanged();
		}
		/*
		 * if (infos.size() < TqmProvider.TQM_CONST_MAX_COUNTS) {
		 * refActivty.getRefresh_layout().setPullLoadEnable(false); }
		 */
	}

	private void showError() {
		// ToastTools.toast(refActivty.getApplicationContext(), refActivty
		// .getApplicationContext().getString(R.string.com_net_error_str),
		// null, null, 0, 0);

		ToastTools.topToast(refActivty,
				ResourceUtil.getString(refActivty, R.string.com_net_error_str),
				ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void showPrompt(int size) {
		String str = size == 0 ? refActivty.getApplicationContext().getString(
				R.string.tqm_txn_list_no_refresh_str) : String.format(
				refActivty.getApplicationContext().getString(
						R.string.tqm_txn_list_refresh_str), size);
		// ToastTools.toast(refActivty.getApplicationContext(), str, null,
		// Gravity.TOP, 0, 150);

		ToastTools.topToast(refActivty,
				str,
				ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	@Override
	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}
}