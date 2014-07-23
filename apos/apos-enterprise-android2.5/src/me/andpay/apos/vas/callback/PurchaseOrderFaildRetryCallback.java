package me.andpay.apos.vas.callback;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.vas.activity.PurchaseOrderFaildActivity;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.apos.vas.flow.model.PurchaseOrderFaildContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;
import android.widget.Toast;

@CallBackHandler
public class PurchaseOrderFaildRetryCallback implements PurchaseOrderCallback {

	private PurchaseOrderFaildActivity purchaseOrderFaildActivity;

	public PurchaseOrderFaildRetryCallback(
			PurchaseOrderFaildActivity purchaseOrderFaildActivity) {
		this.purchaseOrderFaildActivity = purchaseOrderFaildActivity;
	}

	public void networkError() {
		clear();
		Toast toast = Toast.makeText(
				purchaseOrderFaildActivity.getApplicationContext(), "网络异常",
				Toast.LENGTH_LONG);
		toast.show();

	}

	public void placeOrderSuccess(PurchaseOrder purchaseOrder) {
		clear();

		PurchaseOrderFaildContext purchaseOrderFaildContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(
						PurchaseOrderFaildContext.class);
		purchaseOrderFaildContext.setOrderId(purchaseOrder.getOrderId());

		TxnContext txnContext = TiFlowControlImpl.instanceControl()
				.getFlowContextData(TxnContext.class);
		CashPaymentContext cashPaymentContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(CashPaymentContext.class);
		if (txnContext != null) {
			txnContext.setOrderId(purchaseOrder.getOrderId());
		}
		if (cashPaymentContext != null) {
			cashPaymentContext.setOrderId(purchaseOrder.getOrderId());
		}

		TiFlowControlImpl.instanceControl().nextSetup(
				purchaseOrderFaildActivity, FlowConstants.SUCCESS);
	}

	public void clear() {
		if (!purchaseOrderFaildActivity.isFinishing()
				&& purchaseOrderFaildActivity.postDialog.isShowing()) {
			purchaseOrderFaildActivity.postDialog.cancel();
		}
	}

}
