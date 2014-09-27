package me.andpay.apos.tqrm.callback.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.andpay.ac.term.api.pas.CouponRedeemList;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.tqrm.activity.CouponListActivity;
import me.andpay.apos.tqrm.activity.CouponListAdapter;
import me.andpay.apos.tqrm.form.QueryCouponCondForm;
import me.andpay.timobileframework.mvc.ModelAndView;

public class QueryCouponCallbackImpl extends AfterProcessWithErrorHandler {

	public CouponListActivity couponActivity;

	private QueryCouponCondForm form;

	public QueryCouponCallbackImpl(CouponListActivity activity,
			QueryCouponCondForm form) {
		super(activity);
		this.form = form;
		this.couponActivity = activity;
	}

	@Override
	public void afterRequest(ModelAndView mv) {
		List<CouponRedeemList> couponRedeemLists = (ArrayList<CouponRedeemList>) mv
				.getValue("couponResult");
		QueryCouponCondForm form = (QueryCouponCondForm) mv
				.getValue("couponQueryForm");
		LinkedList<CouponRedeemList> orderInfosLink = new LinkedList<CouponRedeemList>();
		orderInfosLink.addAll(couponRedeemLists);
		synchronized (couponActivity) {

			if (couponActivity.couponListAdapter == null) {
				CouponListAdapter adapter = initAdapter(form, orderInfosLink);
				couponActivity.couponListAdapter = adapter;
				couponActivity.refreshLayout.setAdapter(adapter);
			} else {
				couponActivity.couponListAdapter.destory();
				couponActivity.couponListAdapter.addValues(orderInfosLink);
				couponActivity.couponListAdapter.setQueryCouponCondForm(form);
			}

			if (couponRedeemLists == null || couponRedeemLists.size() == 0) {
				couponActivity.showNoData();
				return;
			}
			couponActivity.couponListAdapter.notifyDataSetChanged();
			couponActivity.showListView();
		}

	}

	private CouponListAdapter initAdapter(QueryCouponCondForm form,
			LinkedList<CouponRedeemList> couponRedeemLists) {
		CouponListAdapter adapter = new CouponListAdapter(couponRedeemLists,
				form, (CouponListActivity) activity);
		return adapter;
	}

	@Override
	protected void refreshAfterNetworkError() {

		couponActivity.sendQueryForm(form);
	}

}
