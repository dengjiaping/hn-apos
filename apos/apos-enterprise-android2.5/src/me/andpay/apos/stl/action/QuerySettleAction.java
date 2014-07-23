package me.andpay.apos.stl.action;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import me.andpay.ac.term.api.settle.QuerySettleOrderCond;
import me.andpay.ac.term.api.settle.SettleDataService;
import me.andpay.ac.term.api.settle.SettleOrder;
import me.andpay.ac.term.api.txn.QueryTxnCond;
import me.andpay.ac.term.api.txn.TxnRecord;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.dao.PayTxnInfoConvert;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.stl.form.QuerySettleCondForm;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.util.BeanUtils;

@ActionMapping(domain = QuerySettleAction.DOMAIN_NAME)
public class QuerySettleAction extends SessionKeepAction {

	private SettleDataService settleDataService;

	public static final String DOMAIN_NAME = "/tqrm/settleData.action";
	public static final String QUERY_SETTLE = "querySettleList";
	public static final String QUERY_TXN = "queryTxnList";

	// @Inject
	// public OrderInfoDao orderInfoDao;

	/**
	 * 查询结算信息
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView querySettleList(ActionRequest request)
			throws RuntimeException {
		QuerySettleCondForm condForm = (QuerySettleCondForm) request
				.getParameterValue("settleQueryForm");
		QuerySettleOrderCond queryCond = new QuerySettleOrderCond();
		BeanUtils.copyProperties(condForm, queryCond);
		List<SettleOrder> settleResult = settleDataService.querySettleOrders(
				queryCond, 0, 10);
		ModelAndView mav = new ModelAndView();
		mav.addModelValue("settleResult", settleResult);
		mav.addModelValue("settleQueryForm", condForm);
		return mav;
	}

	public ModelAndView queryTxnList(ActionRequest request) {
		ModelAndView mv = new ModelAndView();

		QueryConditionForm condition = (QueryConditionForm) request
				.getParameterValue("queryConditionForm");
		QueryTxnCond cond = PayTxnInfoConvert
				.convertCondtion2RemoteCond(condition);
		LinkedList<PayTxnInfo> infos = new LinkedList<PayTxnInfo>();

		if (!StringUtil.isEmpty(condition.getAmount())) {
			cond.setSalesAmt(new BigDecimal(condition.getAmount()));
		}
		// 按照TxnId倒序
		cond.setOrders("txnId-");
		List<TxnRecord> results = settleDataService
				.querySettledTxn(cond, 0, 10);
		for (TxnRecord record : results) {
			infos.add(PayTxnInfoConvert.convert(record));
		}

		return mv.addModelValue("txnList", infos).addModelValue(
				"queryConditionForm", condition);
	}

}
