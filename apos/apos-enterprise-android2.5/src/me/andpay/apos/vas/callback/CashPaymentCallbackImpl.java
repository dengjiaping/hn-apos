package me.andpay.apos.vas.callback;

import me.andpay.ac.term.api.shop.PurchaseOrder;
import me.andpay.apos.common.flow.FlowConstants;
import me.andpay.apos.vas.activity.CashPaymentActivity;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import me.andpay.timobileframework.mvc.anno.CallBackHandler;

@CallBackHandler
public class CashPaymentCallbackImpl implements PurchaseOrderCallback {

	private CashPaymentActivity cashPaymentActivity;

	public CashPaymentCallbackImpl(CashPaymentActivity cashPaymentActivity) {
		this.cashPaymentActivity = cashPaymentActivity;
	}

	public void networkError() {
		clear();
		TiFlowControlImpl.instanceControl().nextSetup(cashPaymentActivity,
				FlowConstants.FAILED);

	}

	// public void placeOrderFailed(String errorMsg) {
	// clear();
	// TiFlowControlImpl.instanceControl().nextSetup(cashPaymentActivity,
	// FlowConstants.FAILED);
	// }

	public void placeOrderSuccess(PurchaseOrder purchaseOrder) {
		clear();
		CashPaymentContext cashPaymentContext = TiFlowControlImpl
				.instanceControl().getFlowContextData(CashPaymentContext.class);
		cashPaymentContext.setOrderId(purchaseOrder.getOrderId());
		TiFlowControlImpl.instanceControl().nextSetup(cashPaymentActivity,
				FlowConstants.SUCCESS);
	}

	public void clear() {
		cashPaymentActivity.placeDialog.cancel();
	}

}
