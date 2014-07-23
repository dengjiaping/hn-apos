package me.andpay.apos.common.service;

import android.util.Log;
import me.andpay.ac.term.api.txn.ic.ICTxnService;
import me.andpay.ac.term.api.txn.ic.ICTxnSettleList;
import me.andpay.apos.cdriver.model.AposICCardDataInfo;
import me.andpay.apos.tam.action.txn.TxnHelper;

public class ICCardSumbitSettleLListService {

	private ICTxnService icTxnService;

	public void submitICTxnSettleList(
			final AposICCardDataInfo aposICCardDataInfo, final String txnId) {

		Thread thread = new Thread(new Runnable() {

			public void run() {
				try {
					ICTxnSettleList icTxnSettleList = new ICTxnSettleList();
					icTxnSettleList.setIcDataBase64(TxnHelper
							.genICCardInfoBase64(aposICCardDataInfo));
					icTxnSettleList.setTxnId(txnId);
					icTxnService.submitICTxnSettleList(icTxnSettleList);
				} catch (Exception ex) {
					Log.e(this.getClass().getName(),
							"iccard sumbit settleList error", ex);
				}
			}
		});

		thread.start();

	}
}
