package me.andpay.apos.tqm.activity;

import me.andpay.apos.R;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.cmview.TiSectionListView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.common.log.AposDebugLog;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.callback.QueryTxnListAfterProcessHandler;
import me.andpay.apos.tqm.event.QueryBatchStatusButtonClickController;
import me.andpay.apos.tqm.event.QueryBatchTxnItemClickController;
import me.andpay.apos.tqm.event.QueryBatchTxnRefreshController;
import me.andpay.apos.tqm.event.QueryCodButtonClickController;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
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
import android.view.View.OnLongClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.inject.Inject;

@SuppressWarnings("unchecked")
@ContentView(R.layout.tqm_txn_list_layout)
public class TxnBatchQueryActivity extends AposBaseActivity {

	@InjectView(R.id.list_view)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = QueryBatchTxnItemClickController.class)
	private TiSectionListView tqm_txn_list_lv;

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = QueryBatchTxnRefreshController.class)
	private PullToRefreshLayout refresh_layout;

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

	@InjectView(R.id.tqm_txn_list_condition_btn)
	@EventDelegateArray({
			@EventDelegate(delegateClass = OnClickListener.class, toEventController = QueryCodButtonClickController.class),
			@EventDelegate(delegateClass = OnLongClickListener.class, toEventController = QueryCodButtonClickController.class) })
	Button conditionButton;

	@InjectView(R.id.tqm_txn_list_failed_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = QueryBatchStatusButtonClickController.class)
	private Button failButton;

	@InjectView(R.id.tqm_txn_list_all_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = QueryBatchStatusButtonClickController.class)
	private Button allButton;

	@InjectView(R.id.tqm_txn_list_succ_btn)
	@EventDelegate(delegateClass = OnClickListener.class, toEventController = QueryBatchStatusButtonClickController.class)
	private Button succButton;

	@Inject
	DynamicFieldHelper fieldHelper;

	private Integer selectedStatusId;

	private String selectedStatus;
	/**
	 * 判断是否为条件查询，用于控制右上角button
	 */
	private boolean isHasQueryCondition = false;

	@InjectView(R.id.tqm_hascondition_image)
	public ImageView hasCondImg;

	@Inject
	private PayTxnInfoDao payTxnInfoDao;

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tqm_txn_list_lv.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.vas_purchaseorder_section_layout, tqm_txn_list_lv, false));
		refresh_layout.initView();
		queryAll();
		

	}
	

	@Override
	protected void onResumeProcess() {
		Object flag = getAppContext().getAttribute(
				RuntimeAttrNames.FRESH_TXN_FLAG);
		if (flag != null && StringUtil.isNotBlank(flag.toString())) {
			getAppContext().removeAttribute(
					RuntimeAttrNames.FRESH_TXN_FLAG);
			queryAll();
		}
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
		this.isHasQueryCondition = true;
		// setQueryButton();

		byte[] conditionBytes = data.getByteArrayExtra("queryConditionForm");
		QueryConditionForm form = JacksonSerializer.newPrettySerializer().deserialize(
				QueryConditionForm.class, conditionBytes);
		if (hasCond(form)) {
			hasCondImg.setVisibility(View.VISIBLE);
			form.setHasViewCond(true);
		} else {
			hasCondImg.setVisibility(View.GONE);
			form.setHasViewCond(false);
		}
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
		isHasQueryCondition = false;
		refresh_layout.scrollTo(0, 0);
		// setQueryButton();
		setSelectedStatusId(R.id.tqm_txn_list_all_btn);
		setSelectedStatus(null);
		changeStatusButton();
		hasCondImg.setVisibility(View.GONE);
		QueryConditionForm form = new QueryConditionForm();
		queryBatchTxn(form);
	}

	public void queryBatchTxn(QueryConditionForm form) {
		refresh_layout.setPullRefreshEnable(true);
		refresh_layout.setPullLoadEnable(true);
		// 设置用户选择的查询状态
		form.setStatus(selectedStatus);
		// 生成查询请求，查询出前20条记录
		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("queryConditionForm", form);
		request.open(TqmProvider.TQM_DOMAIN_QUERY,
				isHasQueryCondition ? TqmProvider.TQM_ACTION_QUERY_GETTXNLIST
						: TqmProvider.TQM_ACTION_QUERY_GETTXNLIST_STORAGE, Pattern.async);
		request.callBack(new QueryTxnListAfterProcessHandler(this, form));
		request.submit();
		showProgressView();
	}

	public void changeStatusButton() {
		switch (this.selectedStatusId) {
		case R.id.tqm_txn_list_all_btn:
			allButton
					.setBackgroundResource(R.drawable.com_navtop_tab_button_left_sel_img);
			succButton.setBackgroundResource(R.drawable.com_tab_button_middle_selector);
			failButton.setBackgroundResource(R.drawable.com_tab_button_right_selector);
			allButton
					.setTextColor(getResources().getColor(R.color.lam_button_entrue_col));
			succButton
					.setTextColor(getResources().getColor(R.color.com_title_normal_col));
			failButton
					.setTextColor(getResources().getColor(R.color.com_title_normal_col));
			break;
		case R.id.tqm_txn_list_succ_btn:
			succButton
					.setBackgroundResource(R.drawable.com_navtop_tab_button_middle_sel_img);
			allButton.setBackgroundResource(R.drawable.com_tab_button_left_selector);
			failButton.setBackgroundResource(R.drawable.com_tab_button_right_selector);
			succButton.setTextColor(getResources()
					.getColor(R.color.lam_button_entrue_col));
			allButton.setTextColor(getResources().getColor(R.color.com_title_normal_col));
			failButton
					.setTextColor(getResources().getColor(R.color.com_title_normal_col));
			break;
		case R.id.tqm_txn_list_failed_btn:
			failButton
					.setBackgroundResource(R.drawable.com_navtop_tab_button_right_sel_img);
			allButton.setBackgroundResource(R.drawable.com_tab_button_left_selector);
			succButton.setBackgroundResource(R.drawable.com_tab_button_middle_selector);
			failButton.setTextColor(getResources()
					.getColor(R.color.lam_button_entrue_col));
			allButton.setTextColor(getResources().getColor(R.color.com_title_normal_col));
			succButton
					.setTextColor(getResources().getColor(R.color.com_title_normal_col));
			break;
		}
	}

	public void setQueryButton() {
		// queryAllButton.setVisibility(isHasQueryCondition ? View.VISIBLE
		// : View.GONE);
		conditionButton.setVisibility(isHasQueryCondition ? View.GONE : View.VISIBLE);
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

	public Integer getSelectedStatusId() {
		return selectedStatusId;
	}

	public void setSelectedStatusId(Integer selectedStatusId) {
		this.selectedStatusId = selectedStatusId;
	}

	public boolean isHasQueryCondition() {
		return isHasQueryCondition;
	}

	public void setHasQueryCondition(boolean isHasQueryCondition) {
		this.isHasQueryCondition = isHasQueryCondition;
	}

	public String getSelectedStatus() {
		return selectedStatus;
	}

	public void setSelectedStatus(String selectedStatus) {
		this.selectedStatus = selectedStatus;
	}

	public DynamicFieldHelper getFieldHelper() {
		return fieldHelper;
	}

}
