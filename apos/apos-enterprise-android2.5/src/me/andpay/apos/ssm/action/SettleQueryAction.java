package me.andpay.apos.ssm.action;

import java.util.LinkedList;
import java.util.List;

import me.andpay.ac.consts.TxnTypes;
import me.andpay.ac.term.api.txn.QueryTxnBatchCond;
import me.andpay.ac.term.api.txn.QueryTxnCond;
import me.andpay.ac.term.api.txn.TxnBatch;
import me.andpay.ac.term.api.txn.TxnBatchService;
import me.andpay.ac.term.api.txn.TxnRecord;
import me.andpay.ac.term.api.txn.TxnService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.ti.base.AppBizException;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;

@ActionMapping(domain = "/ssm/query.action")
public class SettleQueryAction extends SessionKeepAction {

	TxnService txnService;

	TxnBatchService txnBatchService;

	/**
	 * 加载用户未结帐汇总信息
	 * 
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	public ModelAndView loadUnSettleInfo(ActionRequest request)
			throws RuntimeException {
		TxnBatch txnBatch = null;
		try {
			txnBatch = txnBatchService.getCurrentBatch();
		} catch (AppBizException e) {
			// TODO 处理
			e.printStackTrace();
		}
		ModelAndView mv = new ModelAndView();
		if (txnBatch == null || txnBatch.getSummary() == null) {
			mv.addModelValue("hasUnSettleInfo", false);
			return mv;
		} else {
			mv.addModelValue("hasUnSettleInfo", true);
		}
		mv.addModelValue("beginDate", txnBatch.getTxnStartTime());
		mv.addModelValue("endDate", txnBatch.getTxnEndTime());
		mv.addModelValue("txnCount", String.valueOf(SettleInfoUtil
				.getItemCount(txnBatch.getItems(), TxnTypes.PURCHASE)));
		mv.addModelValue(
				"txnAmount",
				SettleInfoUtil.getItemTotal(txnBatch.getItems(),
						TxnTypes.PURCHASE).toString());
		mv.addModelValue("cancelCount", String.valueOf(SettleInfoUtil
				.getItemCount(txnBatch.getItems(), TxnTypes.REFUND)));
		mv.addModelValue(
				"cancelAmount",
				String.valueOf(SettleInfoUtil.getItemTotal(txnBatch.getItems(),
						TxnTypes.REFUND).toString()));
		mv.addModelValue("voidCount", String.valueOf(SettleInfoUtil
				.getItemCount(txnBatch.getItems(), TxnTypes.VOID)));
		mv.addModelValue(
				"voidAmount",
				String.valueOf(SettleInfoUtil.getItemTotal(txnBatch.getItems(),
						TxnTypes.VOID).toString()));
		mv.addModelValue("amount", txnBatch.getSummary().getTotal().toString());
		mv.addModelValue("count",
				String.valueOf(txnBatch.getSummary().getCount()));
		return mv;
	}

	/**
	 * 查询批次详情
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView querySettleDetail(ActionRequest request) {
		QuerySettleCondition condition = (QuerySettleCondition) request
				.getParameterValue("condition");
		QueryTxnCond txnCond = new QueryTxnCond();
		if (!StringUtil.isEmpty(condition.getBatch_status())) {
			// txnCond.set
		}
		// if (!StringUtil.isEmpty(condition.getMax_ref_no())) {
		// txnCond.setMaxRefNo(condition.getMax_ref_no());
		// }
		// if (!StringUtil.isEmpty(condition.getMin_ref_no())) {
		// txnCond.setMinRefNo(condition.getMin_ref_no());
		// }
		// if (condition.getBatch_no() != null) {
		// txnCond.setBatchId(condition.getBatch_no());
		// }
		// TODO 还需要增加BatchStatus属性
		List<TxnRecord> records = txnService.queryTxn(txnCond, 0,
				condition.getMax_record_size());
		return new ModelAndView().addModelValue("detailList", records);
	}

	/**
	 * 查询用户结帐汇总信息
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView querySettleInfo(ActionRequest request) {
		String maxBatchNo = (String) request.getParameterValue("maxBatchNo");
		String minBatchNo = (String) request.getParameterValue("minBatchNo");
		Integer recordCounts = (Integer) request
				.getParameterValue("recordCounts");
		QueryTxnBatchCond cond = new QueryTxnBatchCond();
		if (!StringUtil.isEmpty(maxBatchNo)) {
			cond.setMaxBatchId(Long.parseLong(maxBatchNo));
		}
		if (!StringUtil.isEmpty(minBatchNo)) {
			cond.setMinBatchId(Long.parseLong(minBatchNo));
		}
		cond.setOrders("id-");
		List<TxnBatch> batchs = txnBatchService.queryTxnBatch(cond, 0,
				recordCounts);
		LinkedList<TxnBatch> results = new LinkedList<TxnBatch>();
		if (batchs != null) {
			results.addAll(batchs);
		}
		return new ModelAndView().addModelValue("infoList", results);
	}

}
