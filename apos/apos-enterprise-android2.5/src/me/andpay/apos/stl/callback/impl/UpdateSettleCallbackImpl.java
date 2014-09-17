package me.andpay.apos.stl.callback.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.andpay.ac.term.api.settle.SettleOrder;
import me.andpay.apos.R;
import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.stl.activity.SettleListActivity;
import me.andpay.timobileframework.mvc.ModelAndView;

public class UpdateSettleCallbackImpl extends AfterProcessWithErrorHandler {

	public SettleListActivity settleListActivity;

	private boolean refresh;

	public UpdateSettleCallbackImpl(SettleListActivity activity, boolean refresh) {
		super(activity);
		settleListActivity = activity;
		this.refresh = refresh;
	}

	@Override
	public void processNetworkError() {
		stopListView();
		showError();
		
	}

	@Override
	public void afterRequest(ModelAndView mv) {

		List<SettleOrder> couponResult = (ArrayList<SettleOrder>) mv
				.getValue("settleResult");
		LinkedList<SettleOrder> settleOrders = new LinkedList<SettleOrder>();
		settleOrders.addAll(couponResult);

		stopListView();
		onLoad(settleOrders);
	}

	private void stopListView() {
		if (refresh) {
			settleListActivity.refreshLayout.stopRefresh();
		} else {
			settleListActivity.refreshLayout.stopLoadMore();
		}
	}

	private void onLoad(LinkedList<SettleOrder> infos) {
		if (refresh) {
			showPrompt(infos.size());
		}
		if (infos.size() > 0) {
			synchronized (settleListActivity) {
				addDataByRefreshFlag(infos);
				settleListActivity.settleListAdapter.notifyDataSetChanged();
			}
			
		}

	}

	@Override
	protected void processOtherError() {
		stopListView();
		super.processOtherError();
	}

	private void addDataByRefreshFlag(LinkedList<SettleOrder> infos) {
		for (int i = 1; i <= infos.size(); i++) {
			if (refresh) {
				settleListActivity.settleListAdapter.addValue(
						infos.get(infos.size() - i), true);
			} else {
				settleListActivity.settleListAdapter.addValue(infos.get(i - 1), false);
			}
		}
	}

	public void showError() {
		ToastTools.topToast(settleListActivity,
				ResourceUtil.getString(settleListActivity, R.string.com_net_error_str),
				ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void showPrompt(int size) {
		String str = size == 0 ? settleListActivity.getApplicationContext().getString(
				R.string.stl_settle_data_no_refresh_str) : String.format(
						settleListActivity.getApplicationContext().getString(
						R.string.stl_settle_data_no_refresh_str), size);

		ToastTools.topToast(settleListActivity, str, ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	@Override
	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}

}
