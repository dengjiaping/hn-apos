package me.andpay.apos.tqrm.callback.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import me.andpay.ac.term.api.pas.CouponRedeemList;
import me.andpay.apos.R;
import me.andpay.apos.cmview.ToastTools;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.util.ResourceUtil;
import me.andpay.apos.tqrm.activity.CouponListActivity;
import me.andpay.timobileframework.mvc.ModelAndView;

public class UpdateCoupanCallbackImpl extends AfterProcessWithErrorHandler {

	public CouponListActivity couponListActivity;

	private boolean refresh;

	public UpdateCoupanCallbackImpl(CouponListActivity activity, boolean refresh) {
		super(activity);
		couponListActivity = activity;
		this.refresh = refresh;
	}

	@Override
	public void processNetworkError() {
		stopListView();
		showError();
	}

	@Override
	public void afterRequest(ModelAndView mv) {

		List<CouponRedeemList> couponResult = (ArrayList<CouponRedeemList>) mv
				.getValue("couponResult");
		LinkedList<CouponRedeemList> couponRedeemLists = new LinkedList<CouponRedeemList>();
		couponRedeemLists.addAll(couponResult);

		stopListView();
		onLoad(couponRedeemLists);
	}

	private void stopListView() {
		if (refresh) {
			couponListActivity.refreshLayout.stopRefresh();
		} else {
			couponListActivity.refreshLayout.stopLoadMore();
		}
	}

	private void onLoad(LinkedList<CouponRedeemList> infos) {
		if (refresh) {
			showPrompt(infos.size());
		}
		if (infos.size() > 0) {
			synchronized (couponListActivity) {
				addDataByRefreshFlag(infos);
				couponListActivity.couponListAdapter.notifyDataSetChanged();
			}
			/*
			 * couponListActivity
			 * .resetCountTvTitle(couponListActivity.couponListAdapter
			 * .getCount());
			 */
		}

	}

	@Override
	protected void processOtherError() {
		stopListView();
		super.processOtherError();
	}

	private void addDataByRefreshFlag(LinkedList<CouponRedeemList> infos) {
		for (int i = 1; i <= infos.size(); i++) {
			if (refresh) {
				couponListActivity.couponListAdapter.addValue(
						infos.get(infos.size() - i), true);
			} else {
				couponListActivity.couponListAdapter.addValue(infos.get(i - 1), false);
			}
		}
	}

	public void showError() {
		ToastTools.topToast(couponListActivity,
				ResourceUtil.getString(couponListActivity, R.string.com_net_error_str),
				ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	private void showPrompt(int size) {
		String str = size == 0 ? couponListActivity.getApplicationContext().getString(
				R.string.tqrm_coupon_list_no_refresh_str) : String.format(
				couponListActivity.getApplicationContext().getString(
						R.string.tqrm_coupon_list_refresh_str), size);

		ToastTools.topToast(couponListActivity, str, ToastTools.LIST_VIEW_TOAST_HEIGHT);
	}

	@Override
	public boolean isSupport(Thread thread, Throwable ex) {
		return true;
	}

}
