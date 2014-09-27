package me.andpay.apos.tqm.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.andpay.ac.term.api.txn.QueryTxnCond;
import me.andpay.ac.term.api.txn.TxnRecord;
import me.andpay.ac.term.api.txn.TxnService;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.dao.PayTxnInfoConvert;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.apos.tqm.form.QueryConditionForm;
import me.andpay.ti.util.StringUtil;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;

import com.google.inject.Inject;

@ActionMapping(domain = "/tqm/queryRemote.action")
public class QueryRemoteTxnAction extends SessionKeepAction {

	private TxnService txnService;

	@Inject
	private PayTxnInfoDao payTxnInfoDao;

	/**
	 * 查询交易 到服务端查询
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView queryTxnListByRemote(ActionRequest request) {
		return this.queryRemoteTxn(request, false);
	}

	/**
	 * 查询交易 到服务端查询,并且存储远端数据到本地
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView queryTxnListAndStorgeByRemote(ActionRequest request) {
		return this.queryRemoteTxn(request, true);
	}

	/**
	 * 远端查询
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView querySingleTxnByRemote(ActionRequest request) {
		QueryConditionForm condition = (QueryConditionForm) request
				.getParameterValue("queryConditionForm");
		ModelAndView mv = new ModelAndView();
		List<PayTxnInfo> results = queryRemotePayTxnInfo(condition, 1);
		mv.addModelValue("txnList", results);
		if (results != null && !results.isEmpty()) {
			for (PayTxnInfo info : results) {
				updateLocalInfo(info);
			}
		}
		return mv;
	}

	protected ModelAndView queryRemoteTxn(ActionRequest request,
			boolean isStorage) {
		ModelAndView mv = new ModelAndView();
		QueryConditionForm condition = (QueryConditionForm) request
				.getParameterValue("queryConditionForm");

		Integer maxCount = (Integer) request.getAttribute("counts");
		if (maxCount == null) {
			maxCount = (Integer) request.getParameterValue("counts") == null ? TqmProvider.TQM_CONST_MAX_COUNTS
					: (Integer) request.getParameterValue("counts");
		}

		List<PayTxnInfo> infos = queryRemotePayTxnInfo(condition, maxCount);
		mv.addModelValue("remoteTxnList", infos);
		if (isStorage) {
			for (PayTxnInfo payTxnInfo : infos) {
				updateLocalInfo(payTxnInfo);
			}
		}
		return mv;
	}

	/**
	 * 从服务器获取对象
	 * 
	 * @param condition
	 * @param maxCount
	 * @return
	 */
	private List<PayTxnInfo> queryRemotePayTxnInfo(
			QueryConditionForm condition, Integer maxCount) {
		QueryTxnCond cond = PayTxnInfoConvert
				.convertCondtion2RemoteCond(condition);
		List<PayTxnInfo> infos = new ArrayList<PayTxnInfo>();

		if (!StringUtil.isEmpty(condition.getAmount())) {
			cond.setSalesAmt(new BigDecimal(condition.getAmount()));
		}
		// 按照TxnId倒序
		cond.setOrders("txnId-");
		List<TxnRecord> results = txnService.queryTxn(cond, 0, maxCount);
		for (TxnRecord record : results) {
			infos.add(PayTxnInfoConvert.convert(record));
		}
		return infos;
	}

	private synchronized void updateLocalInfo(PayTxnInfo payTxnInfo) {

		QueryPayTxnInfoCond condTxn = new QueryPayTxnInfoCond();
		condTxn.setTermTxnTime(payTxnInfo.getTermTxnTime());
		condTxn.setTermTraceNo(payTxnInfo.getTermTraceNo());
		condTxn.setOperNo(payTxnInfo.getOperNo());
		condTxn.setTxnPartyId(payTxnInfo.getTxnPartyId());
		// condTxn.setSalesAmt(payTxnInfo.getSalesAmt());

		List<PayTxnInfo> payTxnInfosDb = payTxnInfoDao.query(condTxn, 0, 10);
		if (payTxnInfosDb.isEmpty()) {
			payTxnInfoDao.insert(payTxnInfo);
			return;
		}
		PayTxnInfo payTxnInfoDb = payTxnInfosDb.get(0);
		payTxnInfo.setIdTxn(payTxnInfoDb.getIdTxn());
		payTxnInfoDao.update(payTxnInfo);

	}

}
