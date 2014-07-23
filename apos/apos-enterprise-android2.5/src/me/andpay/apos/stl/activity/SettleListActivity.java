package me.andpay.apos.stl.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.cmview.TiSectionListView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.stl.action.QuerySettleAction;
import me.andpay.apos.stl.activity.adapter.SettleListAdapter;
import me.andpay.apos.stl.callback.impl.QuerySettleCallbackImpl;
import me.andpay.apos.stl.event.QuerySettleItemClickController;
import me.andpay.apos.stl.event.QuerySettleRefreshController;
import me.andpay.apos.stl.form.QuerySettleCondForm;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.form.ValueContainer;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;

/**
 * 结算明细
 * @author cpz
 *
 */
@ContentView(R.layout.stl_settle_list_layout)
public class SettleListActivity extends AposBaseActivity implements ValueContainer {

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = QuerySettleRefreshController.class)
	public PullToRefreshLayout refreshLayout;

	@InjectView(R.id.list_view)
	private TiSectionListView orderListView;

	@InjectView(R.id.com_processing_layout)
	public View progressayout;

	@InjectView(R.id.com_list_layout)
	public View listView;
	
	@InjectView(R.id.list_view)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = QuerySettleItemClickController.class)
	public TiSectionListView sectionListView;

	@InjectView(R.id.com_no_data_layout)
	public View noDataLayout;

	public SettleListAdapter settleListAdapter;

	public Integer statusSelectorButtonIndext = 0;

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
	


	public void queryAll() {
		refreshLayout.setPullRefreshEnable(true);
		refreshLayout.setPullLoadEnable(true);
		QuerySettleCondForm settleForm = new QuerySettleCondForm();
		settleForm.setOrders("settleTime-");
		sendQueryForm(settleForm);
	}

	public void sendQueryForm(QuerySettleCondForm settleForm) {

		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("settleQueryForm",  settleForm);
		request.open(QuerySettleAction.DOMAIN_NAME,
				QuerySettleAction.QUERY_SETTLE, Pattern.async);
		request.callBack(new QuerySettleCallbackImpl(this,  settleForm));
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
