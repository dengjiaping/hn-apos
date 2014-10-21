package me.andpay.apos.tqm.action;

import java.util.LinkedList;
import java.util.List;

import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
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
import me.andpay.timobileframework.mvc.context.TiContext;

import com.google.inject.Inject;

@ActionMapping(domain = "/tqm/query.action")
public class QueryTxnAction extends SessionKeepAction {

	@Inject
	private PayTxnInfoDao payTxnDao;

	/**
	 * 查询交易 先从本地查询，如何查询记录不够到服务端查询
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView queryTxnList(ActionRequest request) {
		return this.queryMultipleTxnList(request, false);
	}

	public ModelAndView queryTxnListAndStorageRemote(ActionRequest request) {
		return this.queryMultipleTxnList(request, true);
	}

	@SuppressWarnings("unchecked")
	private ModelAndView queryMultipleTxnList(ActionRequest request,
			boolean isStorage) {
		ModelAndView mv = new ModelAndView();
		Integer maxCount = (Integer) request.getParameterValue("counts");
		if (maxCount == null) {
			maxCount = TqmProvider.TQM_CONST_MAX_COUNTS;
		}
		LinkedList<PayTxnInfo> results = new LinkedList<PayTxnInfo>();
		QueryConditionForm condition = (QueryConditionForm) request
				.getParameterValue("queryConditionForm");
		List<PayTxnInfo> infos = queryLocalPayTxnInfo(condition, maxCount,
				request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION));
		results.addAll(infos);
		if (results.size() < maxCount) {
			String txnId = getLocalMaxTxnId(results);
			condition.setMaxTxnId(StringUtil.isEmpty(txnId) ? condition
					.getMaxTxnId() : txnId);
			request.setAttribute("counts", maxCount - results.size());
			ModelAndView forwardRefundMv;
			try {
				forwardRefundMv = request
						.getDispatcher()
						.forward(
								TqmProvider.TQM_DOMAIN_QUERY_REMOTE,
								isStorage ? TqmProvider.TQM_ACTION_QUERY_REMOTE_GETTXNLIST_STORAGE_REMOTE 
										: TqmProvider.TQM_ACTION_QUERY_REMOTE_GETTXNLIST_REMOTE,
								request);
				List<PayTxnInfo> remoteTxnList = (List<PayTxnInfo>) forwardRefundMv
						.getValue("remoteTxnList");
				for (PayTxnInfo info : remoteTxnList) {
					results.addLast(info);
				}

			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// mv.addModelValue("remoteTxnList", infos);
		}

		return mv.addModelValue("txnList", results).addModelValue(
				"queryConditionForm", condition);
	}

	/**
	 * 从本地加载对象
	 * 
	 * @param condition
	 * @param maxCount
	 * @return
	 */
	private List<PayTxnInfo> queryLocalPayTxnInfo(QueryConditionForm condition,
			Integer maxCount, TiContext context) {
		PartyInfo party = (PartyInfo) context
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		LoginUserInfo info = (LoginUserInfo) context
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		QueryPayTxnInfoCond cond = PayTxnInfoConvert
				.convertCondtion2Cond(condition);
		cond.setTxnPartyId(party.getPartyId());
		cond.setOperNo(info.getUserName());
		List<PayTxnInfo> payInfo = payTxnDao.query(cond, 0, maxCount);
		return payInfo;
	}

	// private void storgePayTxnInfo(List<PayTxnInfo> infos) {
	// if (!syncFlag) {
	// syncFlag = true;
	// if (infos != null && infos.size() > 0) {
	// for (PayTxnInfo info : infos) {
	// payTxnDao.insert(info);
	// }
	// }
	// syncFlag = false;
	// }
	// }

	private String getLocalMaxTxnId(LinkedList<PayTxnInfo> results) {
		if (results.size() == 0) {
			return null;
		}
		for (int i = results.size() - 1; i >= 0; i--) {
			if (!StringUtil.isEmpty(results.get(i).getTxnId())) {
				return results.get(i).getTxnId();
			}
		}
		return null;
	}
}
