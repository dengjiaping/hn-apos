package me.andpay.apos.dao;

import java.util.List;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.timobileframework.sqlite.GenSqLiteDao;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class PurchaseOrderInfoDao extends
		GenSqLiteDao<PurchaseOrderInfo, QueryPurchaseOrderInfoCond, Integer> {

	public PurchaseOrderInfoDao(Context context, String name,
			CursorFactory factory, int version,
			Class<? extends PurchaseOrderInfo> t) {
		super(context, name, factory, version, t);
	}

	public void updatePayTxnInfo2Fulfill(Long orderid) {
		QueryPurchaseOrderInfoCond cond = new QueryPurchaseOrderInfoCond();
		cond.setOrderId(orderid);
		List<PurchaseOrderInfo> purchaseOrderInfos = this.query(cond, 0, 1);
		if (purchaseOrderInfos.size() > 0) {
			PurchaseOrderInfo purchaseOrderInfo = purchaseOrderInfos.get(0);
			purchaseOrderInfo.setStatus(PurchaseOrder.STATUS_FULFILLED);
			this.update(purchaseOrderInfo);
		}
	}

}
