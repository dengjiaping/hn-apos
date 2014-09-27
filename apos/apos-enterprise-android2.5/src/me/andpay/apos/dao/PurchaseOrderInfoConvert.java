package me.andpay.apos.dao;

import me.andpay.ac.term.api.shop.QueryPurchaseOrderCond;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;

public class PurchaseOrderInfoConvert {

	/**
	 * 转换本地查询对象
	 * 
	 * @param cond
	 * @return
	 */
	public static QueryPurchaseOrderCond convert2RemoteCond(
			QueryPurchaseOrderInfoCond cond) {
		QueryPurchaseOrderCond condition = new QueryPurchaseOrderCond();
		condition.setMaxOrderId(cond.getMaxOrderId());
		condition.setMinOrderId(cond.getMinOrderId());
		condition.setSalesAmt(cond.getSalesAmt());
		condition.setPaymentMethod(cond.getPaymentMethod());
		condition.setStartOrderDate(cond.getMinOrderTime());
		condition.setEndOrderDate(cond.getMaxOrderTime());
		return condition;
	}

}
