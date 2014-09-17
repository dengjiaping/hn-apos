package me.andpay.apos.vas.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.apos.tam.flow.model.TxnContext;
import me.andpay.apos.vas.flow.model.OpenCardContext;
import me.andpay.apos.vas.flow.model.ProductSalesContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class SwipeTxnFlowOpenCardTransfer implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {

		ProductSalesContext productSalesContext = TiFlowControlImpl.instanceControl().getFlowContextData(ProductSalesContext.class);
		TxnContext txnContext =  (TxnContext)subFlowContext.get(TxnContext.class.getName());

		OpenCardContext openCardContext = new OpenCardContext();
		if(txnContext.getOrderId() != null) {
			openCardContext.setOrderId(txnContext.getOrderId());
		}else {
//			PurchaseOrderFaildContext purchaseOrderFaildContext = (PurchaseOrderFaildContext)subFlowContext.get(PurchaseOrderFaildContext.class.getName());
//			openCardContext.setOrderId(purchaseOrderFaildContext.getOrderId());
		}
		openCardContext.setCardQuantity(productSalesContext.getShoppingCart().getTotalProduct());
		openCardContext.setProductType(productSalesContext.getShoppingCart().getProductType());
		openCardContext.setCardSalesAmt(txnContext.getSalesAmt());
		TiFlowControlImpl.instanceControl().setFlowContextData(openCardContext);
		
		return null;
	}


}
