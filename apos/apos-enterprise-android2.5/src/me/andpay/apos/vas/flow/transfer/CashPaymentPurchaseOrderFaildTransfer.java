package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.apos.vas.flow.model.PurchaseOrderFaildContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class CashPaymentPurchaseOrderFaildTransfer implements TiFlowNodeDataTransfer {
	
	public Map<String, String> transfterData(Activity activity, Map<String, String> data,
			TiFlowNodeComplete complete, Map<String, Serializable> subFlowContext) {
		
		CashPaymentContext cashPaymentContext = TiFlowControlImpl.instanceControl().getFlowContextData(CashPaymentContext.class);
		
		PurchaseOrderFaildContext purchaseOrderFaildContext = new PurchaseOrderFaildContext();
		TiFlowControlImpl.instanceControl().setFlowContextData(purchaseOrderFaildContext);
		
		purchaseOrderFaildContext.setPaymentMethod(PaymentMethods.CASH);
		purchaseOrderFaildContext.setReceiptNo(cashPaymentContext.getReceiptNo());
		purchaseOrderFaildContext.setShoppingCart(cashPaymentContext.getShoppingCart());
		purchaseOrderFaildContext.setShowOutButton(true);
		purchaseOrderFaildContext.setMsgContent("网络不给力，请重新提交订单信息");
		purchaseOrderFaildContext.setOrderTime(cashPaymentContext.getOrderTime());
		return null;
	}


}
