package me.andpay.apos.stl.callback.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.andpay.ac.term.api.settle.SettleOrder;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.stl.activity.SettleListActivity;
import me.andpay.apos.stl.activity.adapter.SettleListAdapter;
import me.andpay.apos.stl.callback.QuerySettleCallback;
import me.andpay.apos.stl.form.QuerySettleCondForm;
import me.andpay.timobileframework.mvc.ModelAndView;

public class QuerySettleCallbackImpl extends AfterProcessWithErrorHandler
		implements QuerySettleCallback {

	public SettleListActivity settleListActivity;

	private QuerySettleCondForm form;

	public QuerySettleCallbackImpl(SettleListActivity activity,
			QuerySettleCondForm form) {
		super(activity);
		this.form = form;
		this.settleListActivity = activity;
	}

	@Override
	public void afterRequest(ModelAndView mv) {
		List<SettleOrder> settleOrders = (ArrayList<SettleOrder>) mv
				.getValue("settleResult");
		QuerySettleCondForm form = (QuerySettleCondForm) mv
				.getValue("settleQueryForm");
		LinkedList<SettleOrder> orderInfosLink = new LinkedList<SettleOrder>();
		orderInfosLink.addAll(settleOrders);
		synchronized (settleListActivity) {

			if (settleListActivity.settleListAdapter == null) {
				SettleListAdapter adapter = initAdapter(form, orderInfosLink);
				settleListActivity.settleListAdapter = adapter;
				settleListActivity.refreshLayout.setAdapter(adapter);
			} else {
				settleListActivity.settleListAdapter.destory();
				settleListActivity.settleListAdapter.addValues(orderInfosLink);
				settleListActivity.settleListAdapter
						.setQuerySettleCondForm(form);
			}

			if (settleOrders == null || settleOrders.size() == 0) {
				settleListActivity.showNoData();
				return;
			}
			settleListActivity.settleListAdapter.notifyDataSetChanged();
			settleListActivity.showListView();
		}

	}

	private SettleListAdapter initAdapter(QuerySettleCondForm form,
			LinkedList<SettleOrder> settleOrders) {
		SettleListAdapter adapter = new SettleListAdapter(settleOrders, form,
				(SettleListActivity) activity);
		return adapter;
	}

	@Override
	protected void refreshAfterNetworkError() {

		settleListActivity.sendQueryForm(form);
	}

}
