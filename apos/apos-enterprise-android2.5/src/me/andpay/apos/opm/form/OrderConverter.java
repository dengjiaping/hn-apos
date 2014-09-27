package me.andpay.apos.opm.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.andpay.ac.term.api.txn.order.OrderRecord;
import me.andpay.ac.term.api.txn.order.QueryOrderRecordCond;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.dao.model.OrderInfoCond;
import me.andpay.ti.util.DateUtil;

public class OrderConverter {

	public static OrderInfo convertRecord(OrderRecord orderRecord) {

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderId(orderRecord.getOrderId());
		orderInfo.setOrderAmt(orderRecord.getAmt().doubleValue());
		orderInfo.setOrderStatus(orderRecord.getStatus());
		if (orderRecord.getCrtTime() != null) {
			orderInfo.setCreateDate(DateUtil.format("yyyyMMddHHmmss",
					orderRecord.getCrtTime()));
		}
		if (orderRecord.getExpiredTime() != null) {
			orderInfo.setExpiredTime(DateUtil.format("yyyyMMddHHmmss",
					orderRecord.getExpiredTime()));
		}

		orderInfo.setSynDate(DateUtil.format("yyyyMMddHHmmss", new Date()));

		orderInfo.setOrderAttrs1(orderRecord.getOrderAttrs1());
		orderInfo.setOrderAttrs2(orderRecord.getOrderAttrs2());
		orderInfo.setOrderRecordId(orderRecord.getOrderRecordId());
		orderInfo.setUserName(orderRecord.getUserName());
		orderInfo.setPartyId(orderRecord.getTxnPartyId());
		orderInfo.setTxnId(orderRecord.getTxnId());

		return orderInfo;
	}

	public static OrderCondResult convertOrderCondResult(QueryOrderCondForm form) {

		OrderCondResult result = new OrderCondResult();
		OrderInfoCond queryInfoCond = result.getQueryInfoCond();
		queryInfoCond.setOrderStatus(form.getStatus());
		if (form.getAmt() != null) {
			queryInfoCond.setOrderAmt(form.getAmt().doubleValue());
		}
		queryInfoCond.setOrderId(form.getOrderId());
		queryInfoCond.setMaxId(form.getMaxId());
		queryInfoCond.setMinId(form.getMinId());
		queryInfoCond.setUserNameFormat(form.getUserName());
		queryInfoCond.setPartyId(form.getTxnPartyId());
		queryInfoCond.setMaxTxnId(form.getMaxTxnId());
		queryInfoCond.setMinTxnId(form.getMinTxnId());
		queryInfoCond.setSorts(form.getOrders());

		QueryOrderRecordCond queryOrderRecordCond = result
				.getQueryOrderRecordCond();
		queryOrderRecordCond.setAmt(form.getAmt());
		queryOrderRecordCond.setOrderRecordId(form.getOrderRecordId());
		queryOrderRecordCond.setStatus(form.getStatus());
		queryOrderRecordCond.setMaxOrderRecordId(form.getMaxId());
		queryOrderRecordCond.setMinOrderRecordId(form.getMinId());
		queryOrderRecordCond.setMinTxnId(form.getMinTxnId());
		queryOrderRecordCond.setMaxTxnId(form.getMaxTxnId());
		queryOrderRecordCond.setOrders(form.getOrders());

		return result;
	}

	public static List<OrderInfo> mergeOrderList(List<OrderInfo> orderInfos,
			List<OrderRecord> orderRecords) {

		List<OrderInfo> orderInfosMerge = new ArrayList<OrderInfo>();

		if (orderInfos != null) {
			for (OrderInfo orderInfo : orderInfos) {
				orderInfosMerge.add(orderInfo);
			}

		}

		if (orderRecords != null) {
			for (OrderRecord orderRecord : orderRecords) {
				OrderInfo orderInfo = convertRecord(orderRecord);
				orderInfosMerge.add(orderInfo);
			}
		}
		//
		// for (OrderInfo orderInfo : orderInfosMerge) {
		// if(orderInfo.getOrderAttrs1()!=null &&
		// orderInfo.getOrderAttrs1().size()>0) {
		// android.util.Log.d("orderAttr=",
		// orderInfo.getOrderAttrs1().get(0).getName().toString()+"orderId="+orderInfo.getOrderId());
		// }
		// }

		return orderInfosMerge;
	}

}
