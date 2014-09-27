package me.andpay.apos.tqrm.event;

import me.andpay.apos.tqrm.action.QueryCouponAction;
import me.andpay.apos.tqrm.activity.CouponListActivity;
import me.andpay.apos.tqrm.callback.impl.UpdateCoupanCallbackImpl;
import me.andpay.apos.tqrm.form.QueryCouponCondForm;
import me.andpay.timobileframework.mvc.AbstractEventController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.form.FormBean;
import android.app.Activity;

public class QueryCouponRefreshController extends AbstractEventController {

	public void onRefresh(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, true);
	}

	public void onLoadMore(Activity refActivty, FormBean formBean) {
		loadMoreData(refActivty, formBean, false);
	}

	private void loadMoreData(Activity refActivty, FormBean formBean,
			boolean isRefresh) {

		CouponListActivity activity = (CouponListActivity) refActivty;

		EventRequest request = generateSubmitRequest(refActivty);
		request.open(QueryCouponAction.DOMAIN_NAME,
				QueryCouponAction.QUERY_COUPON, Pattern.async);
		QueryCouponCondForm queryCond = activity.couponListAdapter
				.getQueryCouponCondForm();
		if (isRefresh) {
			queryCond.setMaxRedeemId(null);
			queryCond.setMinRedeemId(activity.couponListAdapter
					.getMaxRedeemId());
			queryCond.setOrders("redeemId-");
		} else {
			queryCond.setMinRedeemId(null);
			queryCond.setMaxRedeemId(activity.couponListAdapter
					.getMinRedeemId());
			queryCond.setOrders("redeemId-");

		}
		request.getSubmitData().put("couponQueryForm",
				activity.couponListAdapter.getQueryCouponCondForm());
		request.callBack(new UpdateCoupanCallbackImpl(activity, isRefresh));
		request.submit();
	}
}
