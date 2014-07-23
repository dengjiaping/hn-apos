package me.andpay.apos.stl.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.cmview.TiSectionListView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.event.BackBtnOnclickController;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.stl.action.QuerySettleAction;
import me.andpay.apos.stl.callback.impl.QueryTxnListAfterProcessHandler;
import me.andpay.apos.stl.event.StlTxnItemClickController;
import me.andpay.apos.stl.event.StlTxnRefreshController;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.activity.TxnListAdapter;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.inject.Inject;

@SuppressWarnings("unchecked")
@ContentView(R.layout.stl_txn_list_layout)
public class StlTxnQueryActivity extends AposBaseActivity {

	@InjectView(R.id.list_view)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = StlTxnItemClickController.class)
	private TiSectionListView tqm_txn_list_lv;

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = StlTxnRefreshController.class)
	private PullToRefreshLayout refresh_layout;

	@InjectView(R.id.com_top_back_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = BackBtnOnclickController.class)
	ImageView backBtn;

	private TxnListAdapter adapter;

	@InjectResource(R.string.com_simple_time_str)
	private String dateStr;

	@InjectView(R.id.com_processing_layout)
	View com_progress_layout;

	@InjectView(R.id.com_net_error_layout)
	View com_net_error_layout;

	@InjectView(R.id.com_list_layout)
	View com_list_view;

	@InjectView(R.id.com_no_data_layout)
	View com_no_data_layout;

	@Inject
	DynamicFieldHelper fieldHelper;

	private Long settleOrderId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tqm_txn_list_lv.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.vas_purchaseorder_section_layout, tqm_txn_list_lv,
				false));
		refresh_layout.initView();
		settleOrderId = getIntent().getLongExtra("settleOrderId", 0l);
		queryAll();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.i(this.getClass().getName(), "onActivityResult()");
		if (!(resultCode == Activity.RESULT_OK)) {
			return;
		}
		if (requestCode != TqmProvider.TQM_RESULT_CODE_CONDITION) {
			return;
		}
		if (data == null) {
			return;
		}
		if (adapter != null) {
			adapter.destory();
		}

		byte[] conditionBytes = data.getByteArrayExtra("queryConditionForm");
		QueryConditionForm form = JacksonSerializer.newPrettySerializer()
				.deserialize(QueryConditionForm.class, conditionBytes);

		queryBatchTxn(form);
	}

	private boolean hasCond(QueryConditionForm form) {
		if (StringUtil.isNotBlank(form.getBeginDate())) {
			return true;
		}

		if (StringUtil.isNotBlank(form.getEndDate())) {
			return true;
		}

		if (StringUtil.isNotBlank(form.getOrderno())) {
			return true;
		}

		if (StringUtil.isNotBlank(form.getCardno())) {
			return true;
		}
		if (StringUtil.isNotBlank(form.getTxnId())) {
			return true;
		}

		if (StringUtil.isNotBlank(form.getTxnType())) {
			return true;
		}

		if (StringUtil.isNotBlank(form.getAmount())) {
			return true;
		}
		return false;
	}

	public void queryAll() {
		refresh_layout.scrollTo(0, 0);
		QueryConditionForm form = new QueryConditionForm();
		form.setSettleOrderId(settleOrderId);
		queryBatchTxn(form);
	}

	public void queryBatchTxn(QueryConditionForm form) {
		refresh_layout.setPullRefreshEnable(true);
		refresh_layout.setPullLoadEnable(true);
		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("queryConditionForm", form);
		request.open(QuerySettleAction.DOMAIN_NAME,
				QuerySettleAction.QUERY_TXN, Pattern.async);
		request.callBack(new QueryTxnListAfterProcessHandler(this, form));
		request.submit();
		showProgressView();
	}

	public void showListView() {
		com_list_view.setVisibility(View.VISIBLE);
		com_progress_layout.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
	}

	public void showProgressView() {
		com_list_view.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.VISIBLE);
	}

	public void showNoDataView() {
		com_no_data_layout.setVisibility(View.VISIBLE);
		com_list_view.setVisibility(View.GONE);
		com_net_error_layout.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.GONE);
	}

	public void showNetErrorView() {
		com_net_error_layout.setVisibility(View.VISIBLE);
		com_no_data_layout.setVisibility(View.GONE);
		com_list_view.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.GONE);
	}

	public ListView getTqm_txn_list_lv() {
		return tqm_txn_list_lv;
	}

	public PullToRefreshLayout getRefresh_layout() {
		return refresh_layout;
	}

	public TxnListAdapter getAdapter() {
		return adapter;
	}

	public void setAdapter(TxnListAdapter adapter) {
		this.adapter = adapter;
	}

	public String getDateStr() {
		return dateStr;
	}

	public DynamicFieldHelper getFieldHelper() {
		return fieldHelper;
	}

}
