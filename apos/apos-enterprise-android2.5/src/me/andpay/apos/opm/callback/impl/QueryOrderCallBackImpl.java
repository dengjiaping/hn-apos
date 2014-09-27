package me.andpay.apos.opm.callback.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.opm.activity.OrderPayListActivity;
import me.andpay.apos.opm.activity.OrderPayListAdapter;
import me.andpay.apos.opm.form.QueryOrderCondForm;
import me.andpay.timobileframework.mvc.ModelAndView;
import android.app.Activity;

public class QueryOrderCallBackImpl extends AfterProcessWithErrorHandler {

	public OrderPayListActivity orderActivity;

	private QueryOrderCondForm form;

	public QueryOrderCallBackImpl(Activity activity, QueryOrderCondForm form) {
		super(activity);
		this.form = form;
		this.orderActivity = (OrderPayListActivity) activity;
	}

	@Override
	public void afterRequest(ModelAndView mv) {
		List<OrderInfo> orderInfos = (ArrayList<OrderInfo>) mv
				.getValue("orderResult");
		QueryOrderCondForm form = (QueryOrderCondForm) mv
				.getValue("orderQueryForm");
		LinkedList<OrderInfo> orderInfosLink = new LinkedList<OrderInfo>();
		orderInfosLink.addAll(orderInfos);

		if (orderActivity.orderPayListAdapter == null) {
			OrderPayListAdapter adapter = initAdapter(form, orderInfosLink);
			orderActivity.orderPayListAdapter = adapter;
			orderActivity.refreshLayout.setAdapter(adapter);
		} else {
			orderActivity.orderPayListAdapter.setOrders(orderInfosLink);
			orderActivity.orderPayListAdapter.setQueryOrderCondForm(form);
		}

		if (orderInfos == null || orderInfos.size() == 0) {
			orderActivity.showNoData();
			return;
		}

		orderActivity.orderPayListAdapter.notifyDataSetChanged();
		orderActivity.showListView();

	}

	private OrderPayListAdapter initAdapter(QueryOrderCondForm form,
			LinkedList<OrderInfo> orderInfos) {
		OrderPayListAdapter adapter = new OrderPayListAdapter(orderInfos, form,
				(OrderPayListActivity) activity);
		return adapter;
	}

	@Override
	protected void refreshAfterNetworkError() {

		orderActivity.sendQueryForm(form);
	}

}
