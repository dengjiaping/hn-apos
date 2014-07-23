package me.andpay.apos.opm.callback.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.andpay.apos.R;
import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.opm.activity.OrderPayListActivity;
import me.andpay.timobileframework.mvc.ModelAndView;

public class UpdateOrderDateCallbackImpl extends AfterProcessWithErrorHandler {

	public OrderPayListActivity orderPayListActivity;

	private boolean refresh;

	public UpdateOrderDateCallbackImpl(OrderPayListActivity activity,
			boolean refresh) {
		super(activity);
		orderPayListActivity = activity;
		this.refresh = refresh;
	}
	@Override
	public void processNetworkError() {
		stopListView();
		showError();
	}

	@Override
	public void afterRequest(ModelAndView mv) {

		List<OrderInfo> orderInfos = (ArrayList<OrderInfo>) mv
				.getValue("orderResult");
		LinkedList<OrderInfo> orderInfosLink = new LinkedList<OrderInfo>();
		orderInfosLink.addAll(orderInfos);

		stopListView();
		onLoad(orderInfosLink);
	}

	private void stopListView() {
		if (refresh) {
			orderPayListActivity.refreshLayout.stopRefresh();
		} else {
			orderPayListActivity.refreshLayout.stopLoadMore();
		}
	}

	private void onLoad(LinkedList<OrderInfo> infos) {
		if (refresh) {
			showPrompt(infos.size());
		}
		if (infos.size() > 0) {
			addDataByRefreshFlag(infos);
			orderPayListActivity.resetCountTvTitle(orderPayListActivity.orderPayListAdapter.getCount());
		}

		orderPayListActivity.orderPayListAdapter.notifyDataSetChanged();
	}
	
	@Override
	protected void processOtherError() {
		stopListView();
		super.processOtherError();
	}
	
	
	private void addDataByRefreshFlag(LinkedList<OrderInfo> infos) {
		for (int i = 1; i <= infos.size(); i++) {
			if (refresh) {
				orderPayListActivity.orderPayListAdapter.getOrders().addFirst(infos.get(infos.size() - i));
			} else {
				orderPayListActivity.orderPayListAdapter.getOrders().addLast(infos.get(i - 1));
			}
		}
	}
	

	public void showError() {		
		ToastTools.topToast(orderPayListActivity,
				ResourceUtil.getString(orderPayListActivity, R.string.com_net_error_str),
				ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void showPrompt(int size) {
		String str = size == 0 ? orderPayListActivity.getApplicationContext()
				.getString(R.string.opm_order_list_no_refresh_str) : String
				.format(orderPayListActivity.getApplicationContext().getString(
						R.string.opm_order_list_refresh_str), size);

		ToastTools.topToast(orderPayListActivity, str, ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	@Override
	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}

}
