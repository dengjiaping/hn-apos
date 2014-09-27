package me.andpay.apos.tam.callback.impl;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.cmview.CommonDialog;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.vas.callback.PurchaseOrderCallback;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import me.andpay.timobileframework.mvc.support.TiActivity;

@CallBackHandler
public class TxnPurchaseOrderCallback implements PurchaseOrderCallback {

	private TiActivity tiActivity;
	private CommonDialog placeDialog;

	public TxnPurchaseOrderCallback(TiActivity tiActivity,
			CommonDialog placeDialog) {
		this.tiActivity = tiActivity;
		this.placeDialog = placeDialog;
	}

	public void networkError() {
		clear();
		TiFlowControlImpl.instanceControl().nextSetup(tiActivity,
				FlowConstants.FAILED);
	}

	public void placeOrderSuccess(PurchaseOrder purchaseOrder) {
		clear();
		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		txnContext.setOrderId(purchaseOrder.getOrderId());
		TiFlowControlImpl.instanceControl().nextSetup(tiActivity,
				FlowConstants.SUCCESS);
	}

	public void clear() {
		if (!tiActivity.isFinishing() && placeDialog.isShowing()) {
			placeDialog.cancel();
		}
	}

}
