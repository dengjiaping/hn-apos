package me.andpay.apos.common.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.andpay.apos.dao.OrderInfoDao;
import me.andpay.apos.dao.PayTxnInfoDao;
import me.andpay.apos.dao.PurchaseOrderInfoDao;
import me.andpay.apos.dao.model.OrderInfo;
import me.andpay.apos.dao.model.OrderInfoCond;
import me.andpay.apos.dao.model.PayTxnInfo;
import me.andpay.apos.dao.model.PurchaseOrderInfo;
import me.andpay.apos.dao.model.QueryPayTxnInfoCond;
import me.andpay.apos.dao.model.QueryPurchaseOrderInfoCond;
import me.andpay.ti.util.DateUtil;
import me.andpay.ti.util.StringUtil;

import com.crashlytics.android.Crashlytics;
import com.google.inject.Inject;

/**
 * 交易清除
 * 
 * @author cpz
 * 
 */
public class CleanDataService {

	/**
	 * 
	 */
	@Inject
	public PayTxnInfoDao payTxnDao;

//	@Inject
//	public OrderInfoDao orderInfoDao;

	@Inject
	private PurchaseOrderInfoDao purchaseOrderInfoDao;

	public void cleanTxn() {

		Thread thread = new Thread(new CleanRunner());
		thread.start();

	}

	/**
	 * 删除非当天更新的交易
	 * 
	 * @author cpz
	 * 
	 */
	public class CleanRunner implements Runnable {
		public void run() {
			try{
				clearPayTxnInfo();
//				cleanOrdrInfo();
				cleanPurchaseOrder();
			}catch (Throwable ex){
				Crashlytics.log("clean data error");
				Crashlytics.logException(ex);
			}
		}

//		private void cleanOrdrInfo() {
//			OrderInfoCond orderCond = new OrderInfoCond();
//			orderCond.setEndSynDate(StringUtil.format("yyyyMMddHHmmss",
//					me.andpay.ti.util.DateUtil.roundDate(new Date(),
//							Calendar.DATE)));
//			List<OrderInfo> orderInfos = orderInfoDao.query(orderCond, 0, -1);
//			for (OrderInfo orderInfo : orderInfos) {
//				orderInfoDao.delete(orderInfo.getIdOrder());
//			}
//		}
	}

	private void cleanPurchaseOrder() {
		QueryPurchaseOrderInfoCond cond = new QueryPurchaseOrderInfoCond();
		cond.setEndUpdateTime(DateUtil.rollDate(new Date(), Calendar.DATE, -3));
		List<PurchaseOrderInfo> purchaseOrderInfos = purchaseOrderInfoDao
				.query(cond, 0, -1);
		for (PurchaseOrderInfo purchaseOrderInfo : purchaseOrderInfos) {
			purchaseOrderInfoDao.delete(purchaseOrderInfo.getIdOrder());
		}

	}

	private void clearPayTxnInfo() {
		QueryPayTxnInfoCond cond = new QueryPayTxnInfoCond();
		cond.setEndUpdateTime(StringUtil
				.format("yyyyMMddHHmmss", me.andpay.ti.util.DateUtil.roundDate(
						new Date(), Calendar.DATE)));
		List<PayTxnInfo> payTxns = payTxnDao.query(cond, 0, -1);

		for (PayTxnInfo payTxnInfo : payTxns) {
			payTxnDao.delete(payTxnInfo.getIdTxn());
		}

	}

}
