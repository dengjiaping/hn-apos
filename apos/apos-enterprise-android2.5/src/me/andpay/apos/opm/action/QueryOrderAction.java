package me.andpay.apos.opm.action;

import java.util.ArrayList;
import java.util.List;

import me.andpay.ac.term.api.txn.order.InquiryOrderRequest;
import me.andpay.ac.term.api.txn.order.InquiryOrderResponse;
import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.ac.term.api.txn.order.OrderService;
import me.andpay.ac.term.api.txn.order.QueryOrderRecordCond;
import me.andpay.apos.common.action.SessionKeepAction;
import me.andpay.apos.common.constant.RuntimeAttrNames;
import me.andpay.apos.common.contextdata.LoginUserInfo;
import me.andpay.apos.common.contextdata.PartyInfo;
import me.andpay.apos.dao.OrderInfoDao;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.dao.model.OrderInfoCond;
import me.andpay.apos.opm.callback.InquiryOrderCallback;
import me.andpay.apos.opm.form.OrderCondResult;
import me.andpay.apos.opm.form.OrderConverter;
import me.andpay.apos.opm.form.QueryOrderCondForm;
import me.andpay.ti.base.AppBizException;
import me.andpay.timobileframework.mvc.ModelAndView;
import me.andpay.timobileframework.mvc.action.ActionMapping;
import me.andpay.timobileframework.mvc.action.ActionRequest;
import me.andpay.timobileframework.mvc.context.TiContext;

import com.google.inject.Inject;

@ActionMapping(domain = QueryOrderAction.QUERY_ORDER_ACTION)
public class QueryOrderAction extends SessionKeepAction {

	public static final String QUERY_ORDER_ACTION = "/opm/queryOrder.action";
	public static final String QUERY_ORDER_PAY = "queryOrderPay";
	public static final String INQUIRY_QUERY_ORDER = "inquiryOrder";

	public OrderService orderService;

	// @Inject
	// public OrderInfoDao orderInfoDao;

	/**
	 * 查询订单
	 * 
	 * @param request
	 * @return
	 */
	public ModelAndView queryOrderPay(ActionRequest request)
			throws RuntimeException {
		QueryOrderCondForm condForm = (QueryOrderCondForm) request
				.getParameterValue("orderQueryForm");
		TiContext tiContext = request
				.getContext(TiContext.CONTEXT_SCOPE_APPLICATION);
		PartyInfo partyInfo = (PartyInfo) tiContext
				.getAttribute(RuntimeAttrNames.PARTY_INFO);
		LoginUserInfo userInfo = (LoginUserInfo) tiContext
				.getAttribute(RuntimeAttrNames.LOGIN_USER);
		condForm.setUserName(userInfo.getUserName());
		condForm.setTxnPartyId(partyInfo.getPartyId());

		OrderCondResult condResult = OrderConverter
				.convertOrderCondResult(condForm);
		// List<OrderInfo> orderInfos = orderInfoDao.query(
		// condResult.getQueryInfoCond(), 0, condResult.getMaxResults());

		List<OrderRecord> orderRecords = null;

		QueryOrderRecordCond orderRecordCond = condResult
				.getQueryOrderRecordCond();
		if (orderRecordCond.getStatus().equals(OrderRecord.STATUS_WAITING_PAY)) {

			orderRecords = orderService.queryOrderRecords(
					condResult.getQueryOrderRecordCond(), 0,
					condResult.getIntegerMaxResults());

		}

		if (orderRecordCond.getStatus().equals(OrderRecord.STATUS_PAID)) {

			orderRecords = orderService.queryOrderRecords(
					condResult.getQueryOrderRecordCond(), 0,
					condResult.getIntegerMaxResults());
		}

		// saveOrder(orderRecords, condForm);

		ModelAndView mav = new ModelAndView();
		mav.addModelValue("orderResult",
				OrderConverter.mergeOrderList(new ArrayList<OrderInfo>(), orderRecords));
		mav.addModelValue("orderQueryForm", condForm);
		return mav;
	}

	// private void saveOrder(List<OrderRecord> orderRecords,
	// QueryOrderCondForm condForm) {
	// if (orderRecords == null || orderRecords.isEmpty()) {
	// return;
	// }
	// for (OrderRecord orderRecord : orderRecords) {
	// OrderInfoCond cond = new OrderInfoCond();
	// cond.setUserNameFormat(condForm.getUserName());
	// cond.setPartyId(condForm.getTxnPartyId());
	// cond.setOrderRecordId(orderRecord.getOrderRecordId());
	// List<OrderInfo> orderInfos = orderInfoDao.query(cond, 0, 1);
	// if (orderInfos.size() > 0) {
	// OrderInfo orderInfo = orderInfos.get(0);
	// if (orderInfo.getOrderStatus().equals(condForm.getStatus())) {
	// orderInfo.setOrderStatus(condForm.getStatus());
	// orderInfoDao.update(orderInfo);
	// }
	// } else {
	// orderInfoDao.insert(OrderConverter.convertRecord(orderRecord));
	// }
	// }
	//
	// }

	/**
	 * 查询商户订单
	 * 
	 * @param request
	 * @return
	 * @throws RuntimeException
	 */
	public ModelAndView inquiryOrder(ActionRequest request)
			throws RuntimeException {

		InquiryOrderCallback inquiryOrderCallback = (InquiryOrderCallback) request
				.getHandler();

		try {

			String orderNo = (String) request.getParameterValue("orderNo");

			InquiryOrderRequest inquiryOrderRequest = new InquiryOrderRequest();
			inquiryOrderRequest.setOrderRefNo(orderNo);
			InquiryOrderResponse inquiryOrderResponse = orderService
					.inquiryOrder(inquiryOrderRequest);

			inquiryOrderCallback.querySuccess(inquiryOrderResponse);

		} catch (AppBizException e) {
			inquiryOrderCallback.queryFaild(e.getLocalizedMessage());
		} catch (Exception ex) {
			inquiryOrderCallback.networkError();
		}

		return null;

	}

}
