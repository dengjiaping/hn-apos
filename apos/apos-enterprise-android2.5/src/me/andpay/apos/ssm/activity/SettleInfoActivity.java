package me.andpay.apos.ssm.activity;

import java.util.LinkedList;

import me.andpay.ac.term.api.txn.TxnBatch;
import me.andpay.apos.R;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.ssm.SsmProvider;
import me.andpay.apos.ssm.event.SettleInfoLvController;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

@ContentView(R.layout.ssm_settle_info_layout)
public class SettleInfoActivity extends AposBaseActivity {

	@InjectView(R.id.list_view)
	private ListView detailsList;

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = SettleInfoLvController.class)
	private PullToRefreshLayout refresh_layout;

	@InjectView(R.id.ssm_info_list_count_tv)
	private TextView recordCounts;

	private SsmBaseAdapter<TxnBatch> adapter;

	@InjectResource(R.string.ssm_main_time_str)
	private String dateStr;

	@InjectView(R.id.com_processing_layout)
	View com_progress_layout;

	@InjectView(R.id.com_list_info_layout)
	View com_list_info_view;

	@InjectView(R.id.com_list_layout)
	View com_list_view;

	@InjectView(R.id.com_net_error_layout)
	View com_net_error_layout;

	@InjectView(R.id.com_no_data_layout)
	View com_no_data_layout;

	@InjectView(R.id.com_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	ImageView backBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		refresh_layout.initView();
		refresh_layout.setPullLoadEnable(true);
		refresh_layout.setPullRefreshEnable(false);
		querySettleInfo();

	}

	public void querySettleInfo() {
		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("recordCounts",
				SsmProvider.SSM_MAX_COUNTS_CONST);
		request.open(SsmProvider.SSM_DOMAIN_QUERY,
				SsmProvider.SSM_ACTION_MAIN_QUERYSETTLEINFO, Pattern.async);
		request.callBack(new InfoProcess(this));
		this.showProgressView();
		request.submit();
	}

	/**
	 * 显示查询结果列表
	 */
	public void showListView() {
		com_list_view.setVisibility(View.VISIBLE);
		com_list_info_view.setVisibility(View.VISIBLE);
		com_progress_layout.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
	}

	public void showProgressView() {
		com_list_view.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
		com_list_info_view.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.VISIBLE);
	}

	public void showNoDataView() {
		com_no_data_layout.setVisibility(View.VISIBLE);
		com_list_info_view.setVisibility(View.GONE);
		com_list_view.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.GONE);
	}

	public void showNetErrorView() {
		com_net_error_layout.setVisibility(View.VISIBLE);
		com_list_info_view.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_list_view.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.GONE);
	}

	public SsmBaseAdapter<TxnBatch> getAdapter() {
		return adapter;
	}

	public ListView getDetailsList() {
		return detailsList;
	}

	public String getDateStr() {
		return dateStr;
	}

	public TextView getRecordCounts() {
		return recordCounts;
	}

	public PullToRefreshLayout getRefresh_layout() {
		return refresh_layout;
	}

	class InfoProcess extends AfterProcessWithErrorHandler {

		public InfoProcess(Activity  activity) {
			super(activity);
		}

		public void afterRequest(ModelAndView mv) {
			LinkedList<TxnBatch> details = (LinkedList<TxnBatch>) mv
					.getValue("infoList");
			if (details == null || details.size() == 0) {
				showNoDataView();
				return;
			}
			adapter = new SettleInfoAdapter(details, SettleInfoActivity.this,
					dateStr);
			refresh_layout.setAdapter(adapter);
			recordCounts.setText("" + details.size());
			showListView();
		}

		@Override
		public void refreshAfterNetworkError() {
			showNoDataView();
			querySettleInfo();
		}
	}
}
