package me.andpay.apos.trm.callback;

import java.util.LinkedList;

import me.andpay.apos.common.bug.AfterProcessWithErrorHandler;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.tqm.activity.TxnListAdapter;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.apos.trm.activity.RefundBatchQueryActivity;
import me.andpay.timobileframework.mvc.ModelAndView;

public class AfterRefundQueryCallBackHandler extends AfterProcessWithErrorHandler {

	RefundBatchQueryActivity activity;

	QueryConditionForm form;

	public AfterRefundQueryCallBackHandler(
			RefundBatchQueryActivity refundBatchQueryActivity, QueryConditionForm form) {
		super(refundBatchQueryActivity);
		this.activity = refundBatchQueryActivity;
		this.form = form;
	}

	public void afterRequest(ModelAndView mv) {

		LinkedList<PayTxnInfo> infos = (LinkedList<PayTxnInfo>) mv.getValue("txnList");
		QueryConditionForm form = (QueryConditionForm) mv.getValue("queryConditionForm");
		if (infos == null || infos.size() == 0) {
			activity.showNoDataView();
			if (activity.getAdapter() != null) {
				activity.getAdapter().destory();
			}
		} else {
			activity.showListView();
		}
		synchronized (activity) {
			if (activity.getAdapter() == null) {
				TxnListAdapter adapter = initAdapter(form, infos);
				activity.getRefresh_layout().setAdapter(adapter);
				activity.setAdapter(adapter);
			} else {
				activity.getAdapter().destory();
				activity.getAdapter().setForm(form);
				activity.getAdapter().addValues(infos);
			}
			activity.getAdapter().notifyDataSetChanged();
			activity.getTrm_txn_list_lv().setSelection(0);
		}
		/*
		 * if (infos.size() < TqmProvider.TQM_CONST_MAX_COUNTS) {
		 * activity.getRefresh_layout().setPullLoadEnable(false); }
		 */
	}

	private TxnListAdapter initAdapter(QueryConditionForm form,
			LinkedList<PayTxnInfo> infos) {
		TxnListAdapter adapter = new TxnListAdapter(infos, activity,
				activity.getDateStr(), form);
		// adapter.setFieldHelper(activity.getFieldHelper());
		return adapter;
	}

	@Override
	protected void refreshAfterNetworkError() {
		activity.showNoDataView();
		activity.queryBatchTxn(form);
	}

}
