package me.andpay.apos.tqrm.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.cmview.TiSectionListView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tqrm.action.QueryCouponAction;
import me.andpay.apos.tqrm.callback.impl.QueryCouponCallbackImpl;
import me.andpay.apos.tqrm.event.QueryCouponRefreshController;
import me.andpay.apos.tqrm.event.ShowScanCouponControl;
import me.andpay.apos.tqrm.form.QueryCouponCondForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.flow.TIFLowSignTask;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.inject.Inject;

@ContentView(R.layout.tqrm_coupon_list_layout)
@TIFLowSignTask
public class CouponListActivity extends AposBaseActivity {

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = QueryCouponRefreshController.class)
	public PullToRefreshLayout refreshLayout;

	@InjectView(R.id.list_view)
	private TiSectionListView orderListView;

	@InjectView(R.id.com_processing_layout)
	public View progressayout;

	@InjectView(R.id.com_list_layout)
	public View listView;

	@InjectView(R.id.com_no_data_layout)
	public View noDataLayout;

	// @InjectView(R.id.tqrm_coupon_sure_btn)
	// public View scanButton;

	@InjectView(R.id.tqrm_scan_coupon_relay)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowScanCouponControl.class)
	public RelativeLayout scanCoupan;

	public CouponListAdapter couponListAdapter;

	public Integer statusSelectorButtonIndext = 0;

	@Inject
	public TxnControl txnControl;

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		orderListView.setPinnedHeaderView(LayoutInflater.from(this)
				.inflate(R.layout.vas_purchaseorder_section_layout,
						orderListView, false));
		refreshLayout.initView();
		queryAll();
	}

	@Override
	protected void onResumeProcess() {
		Object flag = getAppContext().getAttribute(
				RuntimeAttrNames.FRESH_COUPON_FLAG);
		if (flag != null && StringUtil.isNotBlank(flag.toString())) {
			getAppContext().removeAttribute(RuntimeAttrNames.FRESH_COUPON_FLAG);
			queryAll();
		}

	}

	public void queryAll() {
		refreshLayout.setPullRefreshEnable(true);
		refreshLayout.setPullLoadEnable(true);
		QueryCouponCondForm couponCondForm = new QueryCouponCondForm();
		couponCondForm.setOrders("redeemId-");
		sendQueryForm(couponCondForm);
	}

	public void sendQueryForm(QueryCouponCondForm couponForm) {

		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("couponQueryForm", couponForm);
		request.open(QueryCouponAction.DOMAIN_NAME,
				QueryCouponAction.QUERY_COUPON, Pattern.async);
		request.callBack(new QueryCouponCallbackImpl(this, couponForm));
		request.submit();
		showProgress();
	}

	/*
	 * public void resetCountTvTitle(Integer counts) { countTextView.setText(""
	 * + counts); }
	 */

	public void showProgress() {
		// listInfoView.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		progressayout.setVisibility(View.VISIBLE);
		noDataLayout.setVisibility(View.GONE);
	}

	public void showNoData() {
		// listInfoView.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		progressayout.setVisibility(View.GONE);
		noDataLayout.setVisibility(View.VISIBLE);
	}

	public void showListView() {
		// listInfoView.setVisibility(View.VISIBLE);
		listView.setVisibility(View.VISIBLE);
		progressayout.setVisibility(View.GONE);
		noDataLayout.setVisibility(View.GONE);
		// resetCountTvTitle(couponListAdapter.getCount());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
