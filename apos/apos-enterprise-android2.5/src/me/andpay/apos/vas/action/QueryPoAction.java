package me.andpay.apos.vas.action;

import java.util.LinkedList;
import java.util.List;

import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.apos.vas.VasProvider;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.context.TiContext;

import com.google.inject.Inject;

@ActionMapping(domain = "/vas/query.action")
public class QueryPoAction extends SessionKeepAction {
	
	public static final String PO_SORTS = "orderId-";

	@Inject
	private PurchaseOrderInfoDao poDao;

	/**
	 * 查询交易 先从本地查询，如何查询记录不够到服务端查询
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryPoList(ActionRequest request) {
		return queryAndStorget(request, false);
	}

	/**
	 * 查询交易 先从本地查询，如何查询记录不够到服务端查询
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView queryPoListAndStorage(ActionRequest request) {
		return queryAndStorget(request, true);
	}

	private ModelAndView queryAndStorget(ActionRequest request, boolean isStorge) {
		ModelAndView mv = new ModelAndView();
		Integer maxCount = VasProvider.VAS_CONST_MAX_COUNTS;
		LinkedList<PurchaseOrderInfo> results = new LinkedList<PurchaseOrderInfo>();
		QueryPurchaseOrderInfoCond cond = (QueryPurchaseOrderInfoCond) request
				.getParameterValue("queryForm");
		List<PurchaseOrderInfo> infos = queryLocalPo(cond, maxCount,
				request.getContext(TiContext.CONTEXT_SCOPE_APPLICATION));
		results.addAll(infos);
		if (results.size() < maxCount) {
			Long orderId = getLocalMaxOrderId(results);
			cond.setMaxOrderId(orderId == null ? cond.getMaxOrderId() : orderId);
			request.setAttribute("counts", maxCount - results.size());
			ModelAndView forwardRefundMv = request.getDispatcher().forward(
					VasProvider.VAS_DOMAIN_QUERY_REMOTE,
					isStorge ? VasProvider.VAS_ACTION_QUERY_GETREMOTEPOLISTSTORAGE
							: VasProvider.VAS_ACTION_QUERY_GETREMOTEPOLIST, request);
			infos = forwardRefundMv.getValue("remotePoList", List.class);
			for (PurchaseOrderInfo info : infos) {
				results.addLast(info);
			}
			mv.addModelValue("remotePoList", infos);
		}

		return mv.addModelValue("poList", results).addModelValue("queryForm", cond);
	}

	/**
	 * 从本地加载对象
	 * 
	 * @param condition
	 * @param maxCount
	 * @return
	 */
	private List<PurchaseOrderInfo> queryLocalPo(QueryPurchaseOrderInfoCond condition,
			Integer maxCount, TiContext context) {
		PartyInfo party = (PartyInfo) context.getAttribute(RuntimeAttrNames.PARTY_INFO);
		LoginUserInfo info = (LoginUserInfo) context
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		condition.setMerchPartyId(party.getPartyId());
		condition.setUserName(info.getUserName());
		condition.setSorts(PO_SORTS);
		List<PurchaseOrderInfo> payInfo = poDao.query(condition, 0, maxCount);
		return payInfo;
	}

	private Long getLocalMaxOrderId(LinkedList<PurchaseOrderInfo> results) {
		if (results.size() == 0) {
			return null;
		}
		for (int i = results.size() - 1; i >= 0; i--) {
			if (results.get(i).getOrderId() != null) {
				return results.get(i).getOrderId();
			}
		}
		return null;
	}
}
