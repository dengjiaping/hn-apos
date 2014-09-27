package me.andpay.apos.vas.action;

import java.util.ArrayList;
import java.util.List;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.ac.term.api.shop.PurchaseOrderService;
import me.andpay.ac.term.api.shop.QueryPurchaseOrderCond;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.dao.PurchaseOrderInfoConvert;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.apos.tqm.TqmProvider;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.util.BeanUtils;

import com.google.inject.Inject;

@ActionMapping(domain = "/vas/queryRemote.action")
public class QueryRemotePoAction extends SessionKeepAction {

	private PurchaseOrderService purchaseOrderService;

	@Inject
	private PurchaseOrderInfoDao purchaseOrderInfoDao;

	/**
	 * 查询交易 到服务端查询,并且存储远端数据到本地
	 * 
	 * @param request
	 *            \
	 * @return
	 */
	public ModelAndView queryPoListByRemote(ActionRequest request) {

		ModelAndView mv = new ModelAndView();
		QueryPurchaseOrderInfoCond condition = (QueryPurchaseOrderInfoCond) request
				.getParameterValue("queryForm");

		Integer maxCount = (Integer) request.getAttribute("counts");
		if (maxCount == null) {
			maxCount = (Integer) request.getParameterValue("counts") == null ? TqmProvider.TQM_CONST_MAX_COUNTS
					: (Integer) request.getParameterValue("counts");
		}

		List<PurchaseOrderInfo> infos = queryRemotePoInfo(condition, maxCount);
		mv.addModelValue("remotePoList", infos);
		return mv;
	}

	public ModelAndView queryPoListByRemoteAndStorge(ActionRequest request) {
		ModelAndView mv = queryPoListByRemote(request);
		List<PurchaseOrderInfo> infos = (List<PurchaseOrderInfo>) mv
				.getValue("remotePoList");
		for (PurchaseOrderInfo info : infos) {
			updateLocalInfo(info);
		}
		return mv;
	}

	/**
	 * 远端查询
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView querySingleTxnByRemote(ActionRequest request) {
		QueryPurchaseOrderInfoCond condition = (QueryPurchaseOrderInfoCond) request
				.getParameterValue("queryForm");
		ModelAndView mv = new ModelAndView();
		List<PurchaseOrderInfo> results = queryRemotePoInfo(condition, 1);
		mv.addModelValue("poList", results);
		return mv;
	}

	/**
	 * 从服务器获取对象
	 * 
	 * @param condition
	 * @param maxCount
	 * @return
	 */
	private List<PurchaseOrderInfo> queryRemotePoInfo(
			QueryPurchaseOrderInfoCond condition, Integer maxCount) {
		QueryPurchaseOrderCond cond = PurchaseOrderInfoConvert
				.convert2RemoteCond(condition);
		List<PurchaseOrderInfo> infos = new ArrayList<PurchaseOrderInfo>();

		// 按照TxnId倒序
		cond.setOrders(QueryPoAction.PO_SORTS);
		List<PurchaseOrder> results = purchaseOrderService.queryPurchaseOrders(
				cond, 0, maxCount);
		for (PurchaseOrder record : results) {
			PurchaseOrderInfo info = new PurchaseOrderInfo();
			BeanUtils.copyProperties(record, info);
			infos.add(info);
		}

		return infos;
	}

	private synchronized void updateLocalInfo(PurchaseOrderInfo info) {

		QueryPurchaseOrderInfoCond cond = new QueryPurchaseOrderInfoCond();
		cond.setOrderId(info.getOrderId());
		cond.setMerchPartyId(info.getMerchPartyId());
		cond.setUserName(info.getUserName());
		List<PurchaseOrderInfo> poInfosDb = purchaseOrderInfoDao.query(cond, 0,
				1);
		if (poInfosDb.isEmpty()) {
			purchaseOrderInfoDao.insert(info);
			return;
		}
		PurchaseOrderInfo poInfoDb = poInfosDb.get(0);
		info.setIdOrder(poInfoDb.getIdOrder());
		purchaseOrderInfoDao.update(info);

	}

}
