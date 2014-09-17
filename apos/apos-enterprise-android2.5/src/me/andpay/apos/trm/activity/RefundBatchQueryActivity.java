package me.andpay.apos.trm.activity;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.apos.R;
import me.andpay.apos.cmview.PullToRefreshLayout;
import me.andpay.apos.cmview.PullToRefreshLayout.IOperationListener;
import me.andpay.apos.cmview.TiSectionListView;
import me.andpay.apos.common.activity.AposBaseActivity;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.event.ShowSliderControl;
import me.andpay.apos.common.util.DynamicFieldHelper;
import me.andpay.apos.dao.PayTxnInfoStatus;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.activity.TxnListAdapter;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.apos.trm.TrmProvider;
import me.andpay.apos.trm.callback.AfterRefundQueryCallBackHandler;
import me.andpay.apos.trm.event.RefundBatchTxnRefreshController;
import me.andpay.apos.trm.event.RefundCodBtClickController;
import me.andpay.apos.trm.event.RefundTxnItemClickController;
import me.andpay.ti.util.JacksonSerializer;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.EventRequest;
import me.andpay.timobileframework.mvc.EventRequest.Pattern;
import me.andpay.timobileframework.mvc.anno.EventDelegate;
import me.andpay.timobileframework.mvc.anno.EventDelegate.DelegateType;
import me.andpay.timobileframework.mvc.anno.EventDelegateArray;
import me.andpay.timobileframework.mvc.form.annotation.FormBind;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * 退款批量查询
 * 
 * @author tinyliu
 * 
 */

@SuppressWarnings("unchecked")
@ContentView(R.layout.trm_refund_list_layout)
@FormBind(formBean = QueryConditionForm.class)
public class RefundBatchQueryActivity extends AposBaseActivity {

	@InjectView(R.id.list_view)
	@EventDelegate(delegateClass = OnItemClickListener.class, toEventController = RefundTxnItemClickController.class)
	private TiSectionListView trm_txn_list_lv;

	@InjectView(R.id.pull_container)
	@EventDelegate(delegateClass = IOperationListener.class, toEventController = RefundBatchTxnRefreshController.class)
	private PullToRefreshLayout refresh_layout;

	private TxnListAdapter adapter;

	@InjectResource(R.string.com_time_str)
	private String dateStr;

	@InjectView(R.id.trm_txn_list_condition_btn)
	@EventDelegateArray({
			@EventDelegate(delegateClass = OnClickListener.class, toEventController = RefundCodBtClickController.class),
			@EventDelegate(delegateClass = OnLongClickListener.class, toEventController = RefundCodBtClickController.class) })
	Button conditionButton;

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

	@InjectView(R.id.tqm_hascondition_image)
	public ImageView hasCondImg;

	@InjectView(R.id.com_show_silder_btn)
	@EventDelegate(type = DelegateType.eventController, isNeedFormBean = false, delegateClass = OnClickListener.class, toEventController = ShowSliderControl.class)
	public ImageView showSilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		trm_txn_list_lv.setPinnedHeaderView(LayoutInflater.from(this).inflate(
				R.layout.vas_purchaseorder_section_layout, trm_txn_list_lv, false));
		refresh_layout.initView();
		queryAll();

	}
	

	@Override
	protected void onResumeProcess() {
		Object flag = getAppContext().getAttribute(
				RuntimeAttrNames.FRESH_REFUND_FLAG);
		if (flag != null && StringUtil.isNotBlank(flag.toString())) {
			getAppContext().removeAttribute(
					RuntimeAttrNames.FRESH_REFUND_FLAG);
			queryAll();
		}
	}

	public void queryAll() {
		QueryConditionForm form = new QueryConditionForm();
		hasCondImg.setVisibility(View.GONE);
		queryBatchTxn(form);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			return true;
		}
		return super.onKeyDown(keyCode, event);
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (!(resultCode == Activity.RESULT_OK)) {
			return;
		}
		if (requestCode != TrmProvider.TRM_RESULT_CODE_CONDITION) {
			return;
		}
		if (data == null) {
			return;
		}
		if (adapter != null) {
			adapter.destory();
			// adapter = null;
		}
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

		com_list_view.setVisibility(View.GONE);
		com_no_data_layout.setVisibility(View.GONE);
		com_progress_layout.setVisibility(View.VISIBLE);

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

		if (StringUtil.isNotBlank(form.getAmount())) {
			return true;
		}
		return false;
	}

	public void queryBatchTxn(QueryConditionForm form) {
		// 生成查询请求，查询出前20条记录
		refresh_layout.setPullRefreshEnable(true);
		refresh_layout.setPullLoadEnable(true);
		form.setStatus(PayTxnInfoStatus.STATUS_SUCCESS);
		form.setTxnType(TxnTypes.PURCHASE);
		form.setRefundEnableFlag(true);
		EventRequest request = this.generateSubmitRequest(this);
		request.getSubmitData().put("queryConditionForm", form);
		request.open(TqmProvider.TQM_DOMAIN_QUERY,
				TqmProvider.TQM_ACTION_QUERY_GETTXNLIST, Pattern.async);
		request.callBack(new AfterRefundQueryCallBackHandler(this, form));
		request.submit();
		showProgressView();
	}

	public ListView getTrm_txn_list_lv() {
		return trm_txn_list_lv;
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

	public View getCom_progress_layout() {
		return com_progress_layout;
	}

	public View getCom_list_view() {
		return com_list_view;
	}

	public View getCom_no_data_layout() {
		return com_no_data_layout;
	}

	public PullToRefreshLayout getRefresh_layout() {
		return refresh_layout;
	}

	public DynamicFieldHelper getFieldHelper() {
		return fieldHelper;
	}

}
