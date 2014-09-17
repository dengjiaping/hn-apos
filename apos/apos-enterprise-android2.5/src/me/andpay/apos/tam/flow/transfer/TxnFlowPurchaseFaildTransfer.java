package me.andpay.apos.tam.flow.transfer;

import java.io.Serializable;
import java.util.Map;

import me.andpay.ac.consts.PaymentMethods;
import me.andpay.apos.tam.flow.model.PostVoucherContext;
import me.andpay.apos.vas.flow.model.PurchaseOrderFaildContext;
import me.andpay.timobileframework.flow.TiFlowNodeComplete;
import me.andpay.timobileframework.flow.TiFlowNodeDataTransfer;
import me.andpay.timobileframework.flow.imp.TiFlowControlImpl;
import android.app.Activity;

public class TxnFlowPurchaseFaildTransfer  implements TiFlowNodeDataTransfer {

	public Map<String, String> transfterData(Activity activity,
			Map<String, String> data, TiFlowNodeComplete complete,
			Map<String, Serializable> subFlowContext) {
		
		PostVoucherContext postVoucherContext = TiFlowControlImpl.instanceControl().getFlowContextData(PostVoucherContext.class);
		
		PurchaseOrderFaildContext purchaseOrderFaildContext = new PurchaseOrderFaildContext();
		TiFlowControlImpl.instanceControl().setFlowContextData(purchaseOrderFaildContext);
		purchaseOrderFaildContext.setPaymentMethod(PaymentMethods.SWIPING);
		purchaseOrderFaildContext.setReceiptNo(postVoucherContext.getReceiptNo());
		purchaseOrderFaildContext.setShoppingCart(postVoucherContext.getShoppingCart());
		purchaseOrderFaildContext.setShowOutButton(false);
		purchaseOrderFaildContext.setMsgContent("网络不给力，请重新提交订单,在提交成功前，不要强制退出此页面。");
		purchaseOrderFaildContext.setTxnId(postVoucherContext.getTxnId());
		purchaseOrderFaildContext.setOrderTime(postVoucherContext.getOrderTime());
		return null;
		
	}

}
