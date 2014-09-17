package me.andpay.apos.opm.activity;

import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.apos.R;
import me.andpay.apos.cdriver.CardReaderManager;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.opm.action.QueryOrderAction;
import me.andpay.apos.opm.callback.impl.QueryOrderCallBackImpl;
import me.andpay.apos.opm.event.QueryClickItemController;
import me.andpay.apos.opm.event.QueryOrderRefreshController;
import me.andpay.apos.opm.event.QueryStatusSelectorController;
import me.andpay.apos.opm.form.QueryOrderCondForm;
import me.andpay.apos.tam.context.TxnControl;
import me.andpay.apos.tam.form.TxnActionResponse;
import me.andpay.timobileframework.flow.TIFLowSignTask;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;

@ContentView(R.layout.opm_orderpay_list_layout)
@TIFLowSignTask
public class OrderPayListActivity extends AposBaseActivity {

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = QueryOrderRefreshController.class)
	public PullToRefreshLayout refreshLayout;

	@InjectView(R.id.list_view)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = QueryClickItemController.class)
	private ListView orderListView;

	@InjectView(R.id.opm_order_haspay_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = QueryStatusSelectorController.class)
	private Button hasPayButton;

	@InjectView(R.id.opm_order_nopay_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = QueryStatusSelectorController.class)
	private Button noPayButton;

	// @InjectView(R.id.opm_order_condition_btn)
	// @EventDelegate(delegateClass = OnClickListener.class, toEventController =
	// OrderShowCondController.class)
	// private Button condButton;

	@InjectView(R.id.opm_txn_list_count_tv)
	TextView countTextView;

	@InjectView(R.id.com_processing_layout)
	View progressayout;

	@InjectView(R.id.com_list_layout)
	View listView;

	@InjectView(R.id.com_list_info_layout)
	View listInfoView;

	@InjectView(R.id.com_no_data_layout)
	View noDataLayout;

	@InjectView(R.id.com_list_count_layout)
	View countLayout;

	public OrderPayListAdapter orderPayListAdapter;

	public Integer statusSelectorButtonIndext = 0;

	@Inject
	public TxnControl txnControl;
	
	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		refreshLayout.initView();
		changeStatusButton();
		queryAll();
	}
	
	
	@Override
	protected void onResumeProcess() {
		fleshView();
	}

	private void fleshView() {
		Object flag = getAppContext().getAttribute(RuntimeAttrNames.NEXT_TXN);
		if(flag != null && flag.toString().equals(RuntimeAttrNames.NEXT_TXN)) {
			getAppContext().removeAttribute(RuntimeAttrNames.NEXT_TXN);
			queryAll();
		}
	}


	public void queryAll() {
		refreshLayout.setPullRefreshEnable(true);
		refreshLayout.setPullLoadEnable(true);
		QueryOrderCondForm orderForm = new QueryOrderCondForm();
		sendQueryForm(orderForm);
		changeStatusButton();
	}

	public void sendQueryForm(QueryOrderCondForm orderForm) {

		changeStatus(orderForm);
		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("orderQueryForm", orderForm);
		request.open(QueryOrderAction.QUERY_ORDER_ACTION,
				QueryOrderAction.QUERY_ORDER_PAY, Pattern.async);
		request.callBack(new QueryOrderCallBackImpl(this, orderForm));
		request.submit();
		showProgress();
	}

	public void changeStatusButton() {
		if (statusSelectorButtonIndext == 0) {
			noPayButton
					.setBackgroundResource(R.drawable.com_navtop_tab_button_left_sel_img);
			hasPayButton
					.setBackgroundResource(R.drawable.com_navtop_tab_button_right_img);
			noPayButton.setTextColor(getResources().getColor(
					R.color.lam_button_entrue_col));
			hasPayButton.setTextColor(getResources().getColor(
					R.color.com_title_normal_col));

		} else if (statusSelectorButtonIndext == 1) {

			noPayButton
					.setBackgroundResource(R.drawable.com_navtop_tab_button_left_img);
			hasPayButton
					.setBackgroundResource(R.drawable.com_navtop_tab_button_right_sel_img);
			hasPayButton.setTextColor(getResources().getColor(
					R.color.lam_button_entrue_col));
			noPayButton.setTextColor(getResources().getColor(
					R.color.com_title_normal_col));
		}
	}

	public void changeStatus(QueryOrderCondForm orderForm) {
		if (statusSelectorButtonIndext == 0) {
			orderForm.setStatus(OrderRecord.STATUS_WAITING_PAY);
			orderForm.setOrders("orderRecordId-");
		} else if (statusSelectorButtonIndext == 1) {
			orderForm.setStatus(OrderRecord.STATUS_PAID);
			orderForm.setOrders("txnId-");
		}
	}

	public void resetCountTvTitle(Integer counts) {
		countTextView.setText("" + counts);
	}

	public void showProgress() {
		listInfoView.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		progressayout.setVisibility(View.VISIBLE);
		noDataLayout.setVisibility(View.GONE);
	}

	public void showNoData() {
		listInfoView.setVisibility(View.GONE);
		listView.setVisibility(View.GONE);
		progressayout.setVisibility(View.GONE);
		noDataLayout.setVisibility(View.VISIBLE);
	}

	public void showListView() {
		listInfoView.setVisibility(View.VISIBLE);
		listView.setVisibility(View.VISIBLE);
		progressayout.setVisibility(View.GONE);
		noDataLayout.setVisibility(View.GONE);
		resetCountTvTitle(orderPayListAdapter.getCount());
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
