package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.apos.vas.flow.model.CashPaymentContext;
import me.andpay.apos.vas.flow.model.OpenCardContext;
import me.andpay.apos.vas.flow.model.PurchaseOrderFaildContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class CardSalesCashPaymentOpenCardTransfer implements TiFlowNodeDataTransfer {
	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {
		
		CashPaymentContext cashPaymentContext  = TiFlowControlImpl.instanceControl().getFlowContextData(CashPaymentContext.class);
		
		OpenCardContext openCardContext = new OpenCardContext();
		if(cashPaymentContext.getOrderId() != null) {
			openCardContext.setOrderId(cashPaymentContext.getOrderId());
		}else {
			PurchaseOrderFaildContext purchaseOrderFaildContext =(PurchaseOrderFaildContext)subFlowContext.get(PurchaseOrderFaildContext.class.getName());
			openCardContext.setOrderId(purchaseOrderFaildContext.getOrderId());
		}
	
		openCardContext.setCardQuantity(cashPaymentContext.getShoppingCart().getTotalProduct());
		openCardContext.setProductType(cashPaymentContext.getShoppingCart().getProductType());
		openCardContext.setCardSalesAmt(cashPaymentContext.getShoppingCart().getTotalAmt());
		TiFlowControlImpl.instanceControl().setFlowContextData(openCardContext);
		return null;
	}

}
